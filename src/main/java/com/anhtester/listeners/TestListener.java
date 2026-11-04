package com.anhtester.listeners;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.CaptureHelper;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.reports.AllureManager;
import com.anhtester.reports.ExtentReportManager;
import com.anhtester.reports.ExtentTestManager;
import com.anhtester.utils.LogUtils;
import com.aventstack.extentreports.Status;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * BT13: Test Listener - chụp màn hình khi test Fail.
 * Đồng thời ghi Log4j (BT14), Extent Report (BT15), Allure (BT16), record video (BT12).
 */
public class TestListener implements ITestListener, ISuiteListener {

    private String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    private String getTestDescription(ITestResult result) {
        String desc = result.getMethod().getDescription();
        return desc != null ? desc : getTestName(result);
    }

    @Override
    public void onStart(ISuite suite) {
        PropertiesHelper.loadAllFiles();
        LogUtils.info("========== Start Suite: " + suite.getName() + " ==========");
    }

    @Override
    public void onFinish(ISuite suite) {
        LogUtils.info("========== End Suite: " + suite.getName() + " ==========");
        ExtentReportManager.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Running test case: " + getTestName(result));
        ExtentTestManager.saveToReport(result.getTestClass().getRealClass().getSimpleName() + " - " + getTestName(result), getTestDescription(result));
        if (ConfigData.VIDEO_RECORD()) {
            CaptureHelper.startRecord(getTestName(result));
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("PASSED: " + getTestName(result));
        ExtentTestManager.logMessage(Status.PASS, getTestName(result) + " is passed");
        if (ConfigData.VIDEO_RECORD()) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String name = getTestName(result);
        LogUtils.error("FAILED: " + name);
        LogUtils.error(result.getThrowable());
        if (ConfigData.SCREENSHOT_FAIL()) {
            CaptureHelper.takeScreenshot("FAIL_" + name);
        }
        ExtentTestManager.addScreenshot(Status.FAIL, name + " is failed");
        ExtentTestManager.logMessage(Status.FAIL, String.valueOf(result.getThrowable()));
        try {
            AllureManager.takeScreenshot("Screenshot " + name);
            AllureManager.saveTextLog("Error", String.valueOf(result.getThrowable()));
        } catch (Exception e) {
            LogUtils.warn("Không đính kèm được screenshot Allure: " + e.getMessage());
        }
        if (ConfigData.VIDEO_RECORD()) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("SKIPPED: " + getTestName(result));
        ExtentTestManager.logMessage(Status.SKIP, getTestName(result) + " is skipped");
        if (ConfigData.VIDEO_RECORD()) {
            CaptureHelper.stopRecord();
        }
    }
}
