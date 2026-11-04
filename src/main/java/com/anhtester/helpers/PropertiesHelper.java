package com.anhtester.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * BÀI TẬP 11: Đọc/ghi file properties.
 * Giá trị truyền bằng -Dkey=value khi chạy Maven sẽ ưu tiên hơn giá trị trong file.
 */
public class PropertiesHelper {
    private static Properties properties;
    private static final String DEFAULT_CONFIG = "src/test/resources/configs/config.properties";

    public static synchronized Properties loadAllFiles() {
        properties = new Properties();
        String path = SystemHelper.getCurrentDir() + DEFAULT_CONFIG;
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Không đọc được file config: " + path, e);
        }
        return properties;
    }

    public static String getValue(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            return systemValue;
        }
        if (properties == null) {
            loadAllFiles();
        }
        String value = properties.getProperty(key);
        return value == null ? null : value.trim();
    }

    public static int getInt(String key, int defaultValue) {
        String value = getValue(key);
        try {
            return value == null ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getValue(key));
    }

    public static synchronized void setValue(String key, String value) {
        if (properties == null) {
            loadAllFiles();
        }
        properties.setProperty(key, value);
        String path = SystemHelper.getCurrentDir() + DEFAULT_CONFIG;
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            properties.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException("Không ghi được file config: " + path, e);
        }
    }
}
