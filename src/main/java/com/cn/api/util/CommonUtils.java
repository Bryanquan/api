package com.cn.api.util;

public class CommonUtils {
    public static boolean isNull(Object object) {
        return object == null ? true : false;
    }

    public static boolean isNotNull(Object object) {
        return object != null ? true : false;
    }

    public static <T> T cast(Object object) {
        return (T) object;
    }
}
