package com.anhtester.reports;

import com.anhtester.helpers.CaptureHelper;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import java.util.Base64;

public class ExtentTestManager {
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized ExtentTest saveToReport(String testName, String description) {
        ExtentTest test = ExtentReportManager.getExtentReports().createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    public static void logMessage(String message) {
        logMessage(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        if (getTest() != null) {
            getTest().log(status, message);
        }
    }

    public static void addScreenshot(Status status, String message) {
        if (getTest() == null) return;
        try {
            String base64 = Base64.getEncoder().encodeToString(CaptureHelper.takeScreenshotBytes());
            getTest().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
        } catch (Exception e) {
            getTest().log(status, message);
        }
    }

    public static void unload() {
        extentTest.remove();
    }
}
