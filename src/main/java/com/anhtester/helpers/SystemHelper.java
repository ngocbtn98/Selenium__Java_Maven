package com.anhtester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemHelper {

    public static String getCurrentDir() {
        return System.getProperty("user.dir") + File.separator;
    }

    public static String getDateTimeNow() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    /** Thay ký tự không hợp lệ trong tên file */
    public static String makeSlug(String text) {
        return text == null ? "" : text.replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

    public static void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
