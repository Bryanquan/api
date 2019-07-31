package com.cn.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
      public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

      public static final List<String> FILE_TYPE = new ArrayList<>();

      static {
          FILE_TYPE.add("pdf");
          FILE_TYPE.add("doc");
          FILE_TYPE.add("ppt");
          FILE_TYPE.add("xls");
          FILE_TYPE.add("xlsx");
          FILE_TYPE.add("pptx");
          FILE_TYPE.add("docx");
          FILE_TYPE.add("txt");
          FILE_TYPE.add("log");
          FILE_TYPE.add("xck");

          FILE_TYPE.add("png");
          FILE_TYPE.add("gif");
          FILE_TYPE.add("jpeg");
          FILE_TYPE.add("jpg");

          FILE_TYPE.add("mp3");
          FILE_TYPE.add("mp4");
          FILE_TYPE.add("flv");

          FILE_TYPE.add("zip");
          FILE_TYPE.add("rar");
      }

    public static String getSuffix(String fullPathOrName) {
        return fullPathOrName.substring(fullPathOrName.lastIndexOf(".") + 1);
    }
      public static boolean checkType(List<String> types, String filename){
             String suffix = getSuffix(filename);
             if (CommonUtils.isNull(types) || types.isEmpty()){
                 types = FILE_TYPE;
             }
             return types.contains(suffix);
      }

    public static void main(String[] args) {
              readFile("E:/LexTeng/Desktop/小诸葛.txt");
    }
      public static void readFile(String path){
          FileInputStream fileInputStream = null;
          FileChannel fileChannel = null;
          try {
              fileInputStream = new FileInputStream(path);//打开文件
              fileChannel = fileInputStream.getChannel();//文件通道
              ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//分配缓冲空间
              fileChannel.read(byteBuffer);//将文件读取缓冲内存空间
          }catch (FileNotFoundException e){
                  logger.error(e.getMessage(),e);
          }catch (IOException e){
                  logger.error(e.getMessage(),e);
          }finally {
              if (CommonUtils.isNotNull(fileInputStream)){
                  try {
                      fileInputStream.close();
                  }catch (IOException e){
                      logger.error(e.getMessage(),e);
                  }
              }
              if (CommonUtils.isNotNull(fileChannel)){
                  try {
                      fileChannel.close();
                  }catch (Exception e){
                      logger.error(e.getMessage(),e);
                  }

              }
          }
      }
}
