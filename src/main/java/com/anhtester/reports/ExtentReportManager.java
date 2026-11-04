package com.anhtester.reports;

import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.helpers.SystemHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * BÀI TẬP 15: Khởi tạo ExtentReports (1 instance cho cả lần chạy).
 */
public class ExtentReportManager {
    private static ExtentReports extentReports;

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
            String path = SystemHelper.getCurrentDir() + PropertiesHelper.getValue("extent.report.path");
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("eCommerce CMS Automation Report");
            reporter.config().setDocumentTitle("Extent Report - Anh Tester");
            reporter.config().setTheme(Theme.STANDARD);
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework", "Selenium Java + TestNG");
            extentReports.setSystemInfo("Author", PropertiesHelper.getValue("author"));
            extentReports.setSystemInfo("Browser", PropertiesHelper.getValue("browser"));
            extentReports.setSystemInfo("URL", PropertiesHelper.getValue("url"));
        }
        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
