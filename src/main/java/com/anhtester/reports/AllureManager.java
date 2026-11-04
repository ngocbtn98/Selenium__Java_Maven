package com.anhtester.reports;

import com.anhtester.drivers.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * BÀI TẬP 16: Đính kèm screenshot/log vào Allure Report.
 * Xem report: mvn allure:serve
 */
public class AllureManager {

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshotPNG() {
        if (DriverManager.getDriver() == null) {
            return new byte[0];
        }
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }
}
