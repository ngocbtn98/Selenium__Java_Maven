package com.anhtester.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

/**
 * BÀI TẬP 15: Quản lý ExtentTest theo từng thread.
 */
public class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = ExtentReportManager.getExtentReports().createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void removeTest() {
        extentTest.remove();
    }

    public static void logMessage(Status status, String message) {
        if (getTest() != null) {
            getTest().log(status, message);
        }
    }

    public static void logMessage(String message) {
        logMessage(Status.INFO, message);
    }

    public static void addScreenshot(Status status, String message, String base64Image) {
        if (getTest() != null && base64Image != null) {
            getTest().log(status, message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image, message).build());
        }
    }
}
