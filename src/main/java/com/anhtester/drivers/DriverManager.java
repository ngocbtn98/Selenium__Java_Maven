package com.anhtester.drivers;

import org.openqa.selenium.WebDriver;

/**
 * Quản lý driver theo ThreadLocal để chạy song song (multi browser) không bị đè driver.
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
