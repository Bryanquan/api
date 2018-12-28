package com.cn.api.service.fdfs.common;

import com.cn.api.Exception.FdfsException;
import com.cn.api.enums.ResultCode;
import com.cn.api.util.CommonUtils;
import com.cn.api.util.FileUtils;
import com.cn.api.util.PropertiesUtils;
import dto.FileResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FdfsClient {
    private static Logger logger = LoggerFactory.getLogger(FdfsClient.class);

    private static String FASTDFS_CONFIG_PATH = "api-config.properties";

    public  static final Map<String, String> EXT_MAP = new HashMap<>();

    public static String httpPrefix;

    private static final long  maxSize = 1024 * 1024 * 1024; // maxSize = 100M

    static {
        try {
            PropertiesUtils.setConfigPath(FASTDFS_CONFIG_PATH);
            String httpUrl =  PropertiesUtils.getConfigValue("fdfs_server_addr");
            int endIndex = httpUrl.indexOf(":");
            httpPrefix = httpUrl.substring(0,endIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String FILENAME = "filename";

    public void init() {
        // image
        EXT_MAP.put("png", "image/png");
        EXT_MAP.put("gif", "image/gif");
        EXT_MAP.put("bmp", "image/bmp");
        EXT_MAP.put("ico", "image/x-ico");
        EXT_MAP.put("jpeg", "image/jpeg");
        EXT_MAP.put("jpg", "image/jpeg");

        // 压缩文件
        EXT_MAP.put("zip", "application/zip");
        EXT_MAP.put("rar", "application/x-rar");

        // doc
        EXT_MAP.put("pdf", "application/pdf");
        EXT_MAP.put("ppt", "application/vnd.ms-powerpoint");
        EXT_MAP.put("xls", "application/vnd.ms-excel");
        EXT_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EXT_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        EXT_MAP.put("doc", "application/msword");
        EXT_MAP.put("doc", "application/wps-office.doc");
        EXT_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        EXT_MAP.put("txt", "text/plain");

        //xml
        EXT_MAP.put("xml", "application/xml");
        EXT_MAP.put("xck",  "application/xml");

        //html
        EXT_MAP.put("html", "application/xhtml+xml");

        // 音频
        EXT_MAP.put("mp4", "video/mp4");
        EXT_MAP.put("flv", "video/x-flv");
    }

    public FdfsClient(){
        init();
    }

    private StorageClient1 getStorageClient1(TrackerServer trackerServer) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        return new StorageClient1(trackerServer, storageServer);
    }

    private StorageClient getStorageClient(TrackerServer trackerServer) throws IOException{
        TrackerClient trackerClient = new TrackerClient();
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        return new StorageClient(trackerServer, storageServer);
    }
    public String upload(MultipartFile file) throws IOException {
        if (file.getSize() > maxSize){
            throw new FdfsException(ResultCode.FILE_OUT_SIZE);
        }
        return upload(file, null);
    }

    public String upload(MultipartFile file, Map<String, String> descriptions) throws IOException {
        return upload(file.getInputStream(), file.getOriginalFilename(), descriptions);
    }
    // 返回: 组名+文件路径，如：group1/M00/00/00/wKgz6lnduTeAMdrcAAEoRmXZPp870.jpeg
    public String upload(InputStream inputStream, String filename, Map<String, String> descriptions) throws IOException {
        String path = "";
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        if (CommonUtils.isNull(inputStream)){
            throw new FdfsException(ResultCode.FILE_NOT_EXIST);
        }
        try {
            //读入流
            byte[] inputByte = new byte[inputStream.available()];
            inputStream.read(inputByte, 0 , inputByte.length);
            String fileSuffix = FileUtils.getSuffix(filename);
            StorageClient1 storageClient1 = getStorageClient1(trackerServer);
            path = storageClient1.upload_file1(inputByte, fileSuffix, null);
            if (StringUtils.isBlank(path)){
                throw  new FdfsException(ResultCode.FILE_UPLOAD_FAILED);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("message = {}", e.getMessage());
            throw new FdfsException(ResultCode.FILE_UPLOAD_FAILED);
        }finally {
            if (CommonUtils.isNotNull(inputStream)){
                inputStream.close();
            }
        }
        TrackerServerPool.returnObject(trackerServer);
        return path;
    }

    public FileResponseDto download(String fileUrl, HttpServletResponse response, String filename) throws IOException {
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient1 = getStorageClient1(trackerServer);
        byte[] fileByte = null;
        OutputStream os = null;
        InputStream inputStream = null;
        try {
            fileByte = storageClient1.download_file1(fileUrl);
            if (CommonUtils.isNull(fileByte)){
                throw new FdfsException(ResultCode.FILE_DOWNLOAD_FAILED);
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        try{
            String fileExt = FileUtils.getSuffix(fileUrl);
            String contenType = EXT_MAP.get(fileExt);
            response.reset();
            response.setCharacterEncoding("GBK");
            response.setContentType(contenType);
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength(fileByte.length);
            os = response.getOutputStream();
            inputStream =  new ByteArrayInputStream(fileByte);
            byte[] buffer = new byte[fileByte.length];
            int len = inputStream.read(buffer);
            while (len != -1){
                os.write(buffer, 0 , len);
                len = inputStream.read(buffer);
            }
            os.flush();
            inputStream.close();
        }catch (Exception e){
            logger.error("message = {} || cause = {}", e.getMessage(), e.getCause());
        }finally {
            if (CommonUtils.isNotNull(os)){
                os.close();
            }
        }
        FileResponseDto responseDto = new FileResponseDto();
        responseDto.setCode(ResultCode.FILE_DOWNLOAD_SUCCESS.getCode());
        responseDto.setMessage(ResultCode.FILE_DOWNLOAD_SUCCESS.getMsg());
        responseDto.setFilename(filename);
        responseDto.setFiletype(FileUtils.getSuffix(filename));
        responseDto.setSuccess(true);
        TrackerServerPool.returnObject(trackerServer);
        return responseDto;
    }

    public FileResponseDto delete(String fileUrl, String filename) throws IOException {
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient1 = getStorageClient1(trackerServer);
        int success = 0;
        try {
            success=  storageClient1.delete_file1(fileUrl);
        } catch (MyException e) {
            logger.error("message = {} || cause = {}", e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        if (success != 0){
            throw new FdfsException(ResultCode.FILE_DELETE_FAILED);
        }
        FileResponseDto responseDto = new FileResponseDto();
        responseDto.setMessage(ResultCode.FILE_DELETE_SUCCESS.getMsg());
        responseDto.setCode(ResultCode.FILE_DELETE_SUCCESS.getCode());
        responseDto.setFilepath(fileUrl);
        responseDto.setFiletype(FileUtils.getSuffix(filename));
        responseDto.setFilename(filename);
        responseDto.setSuccess(true);
        TrackerServerPool.returnObject(trackerServer);
        return responseDto;
    }
}
