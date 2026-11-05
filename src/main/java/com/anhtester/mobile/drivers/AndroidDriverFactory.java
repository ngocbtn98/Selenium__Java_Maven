package com.anhtester.mobile.drivers;

import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.helpers.SystemHelper;
import com.anhtester.utils.LogUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Mobile AOS: khởi tạo AndroidDriver (UiAutomator2) từ config.properties.
 * Mọi giá trị đều có thể ghi đè bằng -Dkey=value hoặc parameter trong suite XML.
 */
public class AndroidDriverFactory {

    private AndroidDriverFactory() {
    }

    public static AndroidDriver createDriver(String udid) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(value("android.device.name", "Android Emulator"))
                .setNoReset(PropertiesHelper.getBoolean("android.no.reset"))
                .setAutoGrantPermissions(PropertiesHelper.getBoolean("android.auto.grant.permissions"))
                .setNewCommandTimeout(Duration.ofSeconds(PropertiesHelper.getInt("android.new.command.timeout", 120)));

        String deviceUdid = isBlank(udid) ? PropertiesHelper.getValue("android.udid") : udid;
        if (!isBlank(deviceUdid)) {
            options.setUdid(deviceUdid);
        }
        String platformVersion = PropertiesHelper.getValue("android.platform.version");
        if (!isBlank(platformVersion)) {
            options.setPlatformVersion(platformVersion);
        }

        String appPackage = PropertiesHelper.getValue("android.app.package");
        String appActivity = PropertiesHelper.getValue("android.app.activity");
        if (!isBlank(appPackage)) {
            options.setAppPackage(appPackage);
        }
        if (!isBlank(appActivity)) {
            options.setAppActivity(appActivity);
        }

        String app = PropertiesHelper.getValue("android.app");
        if (!isBlank(app)) {
            File apk = new File(app);
            if (!apk.isAbsolute()) {
                apk = new File(SystemHelper.getCurrentDir() + app);
            }
            if (apk.exists()) {
                options.setApp(apk.getAbsolutePath());
            } else {
                LogUtils.warn("Không tìm thấy APK: " + apk.getAbsolutePath()
                        + " -> mở app đã cài theo appPackage/appActivity");
            }
        }

        String serverUrl = value("appium.server.url", "http://127.0.0.1:4723");
        LogUtils.info("Khởi tạo AndroidDriver: " + serverUrl + " | " + options.asMap());
        try {
            return new AndroidDriver(new URL(serverUrl), options);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("appium.server.url không hợp lệ: " + serverUrl, e);
        }
    }

    private static String value(String key, String defaultValue) {
        String v = PropertiesHelper.getValue(key);
        return isBlank(v) ? defaultValue : v;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
