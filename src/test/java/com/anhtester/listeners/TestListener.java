package com.anhtester.listeners;

import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.CaptureHelper;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.reports.AllureManager;
import com.anhtester.reports.ExtentReportManager;
import com.anhtester.reports.ExtentTestManager;
import com.anhtester.utils.LogUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * BÀI TẬP 13: Test Listener - chụp màn hình khi Fail.
 * BÀI TẬP 15, 16: ghi Extent Report + đính kèm screenshot vào Allure.
 */
public class TestListener implements ITestListener {

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }

    @Override
    public void onStart(ITestContext context) {
        LogUtils.info("===== Bắt đầu suite/test: " + context.getName() + " =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        LogUtils.info("===== Kết thúc: " + context.getName()
                + " | Pass: " + context.getPassedTests().size()
                + " | Fail: " + context.getFailedTests().size()
                + " | Skip: " + context.getSkippedTests().size() + " =====");
        ExtentReportManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String name = getTestName(result);
        LogUtils.info("---> Start test: " + name);
        String description = result.getMethod().getDescription();
        ExtentTestManager.createTest(name, description == null ? "" : description);
        ExtentTestManager.getTest().assignCategory(result.getTestClass().getRealClass().getSimpleName());
        ExtentTestManager.getTest().assignAuthor(PropertiesHelper.getValue("author"));
        CaptureHelper.startRecord(name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("<--- PASS: " + getTestName(result));
        ExtentTestManager.logMessage(Status.PASS, getTestName(result) + " is passed.");
        CaptureHelper.stopRecord();
        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String name = getTestName(result);
        LogUtils.error("<--- FAIL: " + name + " | " + result.getThrowable());

        if (PropertiesHelper.getBoolean("screenshot.fail")) {
            CaptureHelper.takeScreenshot("FAIL_" + name);
        }
        if (DriverManager.getDriver() != null) {
            try {
                String base64 = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
                ExtentTestManager.addScreenshot(Status.FAIL, name, base64);
                AllureManager.takeScreenshotPNG();
            } catch (Exception e) {
                LogUtils.error("Không chụp được màn hình lỗi: " + e.getMessage());
            }
        }
        ExtentTestManager.logMessage(Status.FAIL, String.valueOf(result.getThrowable()));
        AllureManager.saveTextLog(String.valueOf(result.getThrowable()));
        CaptureHelper.stopRecord();
        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("<--- SKIP: " + getTestName(result));
        ExtentTestManager.logMessage(Status.SKIP, getTestName(result) + " is skipped.");
        CaptureHelper.stopRecord();
        ExtentTestManager.removeTest();
    }
}
