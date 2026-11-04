package com.anhtester.common;

import com.anhtester.constants.ConfigData;
import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.listeners.TestListener;
import com.anhtester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * BT8: SetupBrowser chạy multi browser (chrome, firefox, edge).
 * Browser lấy từ parameter của suite XML, nếu không có thì lấy từ config.properties (BT11).
 */
@Listeners(TestListener.class)
public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        PropertiesHelper.loadAllFiles();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void createDriver(@Optional("") String browser) {
        String browserName = (browser == null || browser.isEmpty()) ? ConfigData.BROWSER() : browser;
        WebDriver driver = setupDriver(browserName.trim().toLowerCase());
        DriverManager.setDriver(driver);
        LogUtils.info("Created driver: " + browserName + " | headless = " + ConfigData.HEADLESS());
    }

    public WebDriver setupDriver(String browserName) {
        WebDriver driver;
        switch (browserName) {
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            case "chrome":
            default:
                driver = initChromeDriver();
                break;
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigData.PAGE_LOAD_TIMEOUT()));
        return driver;
    }

    private WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--remote-allow-origins=*");
        // Tuỳ chọn: chỉ định file chạy Chrome khác (để trống = dùng Chrome cài trên máy)
        String chromeBinary = PropertiesHelper.getValue("chrome_binary");
        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            options.setBinary(chromeBinary);
        }
        if (ConfigData.HEADLESS()) {
            options.addArguments("--headless=new", "--no-sandbox", "--window-size=1920,1080");
        }
        WebDriver driver = new ChromeDriver(options);
        if (!ConfigData.HEADLESS()) driver.manage().window().maximize();
        return driver;
    }

    private WebDriver initFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (ConfigData.HEADLESS()) {
            options.addArguments("-headless", "--width=1920", "--height=1080");
        }
        WebDriver driver = new FirefoxDriver(options);
        if (!ConfigData.HEADLESS()) driver.manage().window().maximize();
        return driver;
    }

    private WebDriver initEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        if (ConfigData.HEADLESS()) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        WebDriver driver = new EdgeDriver(options);
        if (!ConfigData.HEADLESS()) driver.manage().window().maximize();
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        DriverManager.quit();
        LogUtils.info("Closed driver");
    }
}
