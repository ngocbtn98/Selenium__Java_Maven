package com.anhtester.reports;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.SystemHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/** BT15: Extent Report */
public class ExtentReportManager {
    private static ExtentReports extentReports;

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
            ExtentSparkReporter reporter = new ExtentSparkReporter(SystemHelper.getCurrentDir() + ConfigData.EXTENT_REPORT_PATH());
            reporter.config().setReportName("eCommerce CMS Automation Report");
            reporter.config().setDocumentTitle("Extent Report - Anh Tester");
            reporter.config().setTheme(Theme.STANDARD);
            reporter.config().setEncoding("UTF-8");
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework", "Selenium Java TestNG POM");
            extentReports.setSystemInfo("Browser", ConfigData.BROWSER());
            extentReports.setSystemInfo("URL", ConfigData.URL());
        }
        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
