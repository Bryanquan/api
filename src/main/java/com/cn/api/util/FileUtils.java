package com.cn.api.util;

import java.util.ArrayList;
import java.util.List;

public class FileUtils {

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


}
