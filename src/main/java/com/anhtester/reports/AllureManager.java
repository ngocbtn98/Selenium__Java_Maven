package com.anhtester.reports;

import com.anhtester.helpers.CaptureHelper;
import io.qameta.allure.Attachment;

/** BT16: Allure Report - đính kèm screenshot/text */
public class AllureManager {
    @Attachment(value = "{name}", type = "image/png")
    public static byte[] takeScreenshot(String name) {
        return CaptureHelper.takeScreenshotBytes();
    }

    @Attachment(value = "{name}", type = "text/plain")
    public static String saveTextLog(String name, String message) {
        return message;
    }
}
