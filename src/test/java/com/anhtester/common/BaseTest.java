package com.anhtester.common;

import com.anhtester.drivers.DriverManager;
import com.anhtester.drivers.SetupBrowser;
import com.anhtester.helpers.ExcelHelper;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.listeners.TestListener;
import com.anhtester.utils.LogUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * BÀI TẬP 7, 8, 11: BaseTest dùng SetupBrowser + DriverManager (ThreadLocal).
 * Browser lấy từ parameter trong suite XML, nếu không có thì lấy trong config.properties.
 */
@Listeners(TestListener.class)
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        PropertiesHelper.loadAllFiles();
        ExcelHelper.createDefaultTestDataFile(PropertiesHelper.getValue("excel.data.path"));
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void createDriver(@Optional("") String browser) {
        DriverManager.setDriver(SetupBrowser.createDriver(browser));
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        LogUtils.info("Đóng browser");
        DriverManager.quit();
    }
}
