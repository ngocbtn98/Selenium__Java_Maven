package com.anhtester.constants;

import com.anhtester.helpers.PropertiesHelper;

/** BT11: Các giá trị config lấy từ config.properties */
public class ConfigData {
    public static String BROWSER() { return PropertiesHelper.getValue("browser"); }
    public static boolean HEADLESS() { return Boolean.parseBoolean(PropertiesHelper.getValue("headless")); }
    public static String URL() { return PropertiesHelper.getValue("url"); }
    public static String EMAIL() { return PropertiesHelper.getValue("email"); }
    public static String PASSWORD() { return PropertiesHelper.getValue("password"); }
    public static int EXPLICIT_WAIT() { return Integer.parseInt(PropertiesHelper.getValue("explicit_wait")); }
    public static int PAGE_LOAD_TIMEOUT() { return Integer.parseInt(PropertiesHelper.getValue("page_load_timeout")); }
    public static String EXCEL_DATA_FILE() { return PropertiesHelper.getValue("excel_data_file"); }
    public static String SCREENSHOT_PATH() { return PropertiesHelper.getValue("screenshot_path"); }
    public static boolean SCREENSHOT_ALL_STEP() { return Boolean.parseBoolean(PropertiesHelper.getValue("screenshot_all_step")); }
    public static boolean SCREENSHOT_FAIL() { return Boolean.parseBoolean(PropertiesHelper.getValue("screenshot_fail")); }
    public static boolean VIDEO_RECORD() { return Boolean.parseBoolean(PropertiesHelper.getValue("video_record")); }
    public static String VIDEO_PATH() { return PropertiesHelper.getValue("video_path"); }
    public static String IMAGE_NAME() { return PropertiesHelper.getValue("image_name"); }
    public static String EXTENT_REPORT_PATH() { return PropertiesHelper.getValue("extent_report_path"); }
}
