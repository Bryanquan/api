package com.cn.api.service.fdfs.api;

import com.cn.api.enums.ResultCode;
import com.cn.api.service.fdfs.common.FdfsClient;
import com.cn.api.util.FileUtils;
import com.cn.api.util.ResultUtils;
import dto.FileResponseDto;
import dto.ResultDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FastDfs文件Api调用实例
 */

@Controller
@RequestMapping("/fdfsApi")
public class FdfsApi {

    private FdfsClient apiClient = new FdfsClient();

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public ResultDto upload(@RequestParam("file") MultipartFile file, String sysid, String group1id, String group2id,
                            String sysname, String group1name, String group2name) throws IOException {
        String filename = file.getOriginalFilename();
        if (!FileUtils.checkType(null, filename)){
            return ResultUtils.error(ResultCode.FILE_TYPE_ERROR);
        }

        String fileUrl = apiClient.upload(file);//返回文件在服务器上的路径
        String fileformat = FileUtils.getSuffix(filename);//文件类型,返回文件后缀名
        return null;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "文件下载", notes = "文件下载")
    public ResultDto download(String fileUrl, HttpServletResponse response) throws IOException {
        String filename = "";
        FileResponseDto fileResponseDto = apiClient.download(fileUrl, response, filename);
        return ResultUtils.success(fileResponseDto);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "文件删除", notes = "文件删除")
    public ResultDto delete(String fileUrl) throws IOException {
        String filename = "";
        FileResponseDto responseDto = apiClient.delete(fileUrl,filename);
        return ResultUtils.success(responseDto);
    }

}
