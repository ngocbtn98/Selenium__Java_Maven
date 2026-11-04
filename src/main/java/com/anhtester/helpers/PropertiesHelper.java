package com.anhtester.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Properties;

/** BT11: Đọc ghi file properties */
public class PropertiesHelper {
    private static Properties properties;
    private static final String DEFAULT_FILE = "src/test/resources/configs/config.properties";
    private static String linkFile;

    public static Properties loadAllFiles() {
        LinkedList<String> files = new LinkedList<>();
        files.add(DEFAULT_FILE);
        try {
            properties = new Properties();
            for (String file : files) {
                Properties temp = new Properties();
                linkFile = SystemHelper.getCurrentDir() + file;
                try (InputStreamReader reader = new InputStreamReader(new FileInputStream(linkFile), StandardCharsets.UTF_8)) {
                    temp.load(reader);
                }
                properties.putAll(temp);
            }
            return properties;
        } catch (Exception e) {
            throw new RuntimeException("Không đọc được file properties: " + linkFile, e);
        }
    }

    public static void setFile(String relPropertiesFilePath) {
        properties = new Properties();
        try {
            linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePath;
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(linkFile), StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (Exception e) {
            throw new RuntimeException("Không đọc được file properties: " + linkFile, e);
        }
    }

    public static String getValue(String key) {
        if (properties == null) {
            loadAllFiles();
        }
        // Cho phép override bằng -Dkey=value khi chạy mvn
        String system = System.getProperty(key);
        if (system != null && !system.isEmpty()) {
            return system;
        }
        return properties.getProperty(key);
    }

    public static void setValue(String key, String value) {
        if (properties == null) {
            loadAllFiles();
        }
        try (FileOutputStream out = new FileOutputStream(linkFile == null ? SystemHelper.getCurrentDir() + DEFAULT_FILE : linkFile)) {
            properties.setProperty(key, value);
            properties.store(out, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
