package com.cn.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {


    private static String CONFIG_PATH;

    public static String getConfigPath() {
        return CONFIG_PATH;
    }

    public static void setConfigPath(String configPath) {
        CONFIG_PATH = configPath;
    }


    public static String getConfigValue(String key) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}
