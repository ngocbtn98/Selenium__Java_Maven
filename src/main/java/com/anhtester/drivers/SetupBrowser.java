package com.anhtester.drivers;

import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * BÀI TẬP 8: Setup browser chạy multi browser (chrome, edge, firefox) + headless.
 * Driver được tự tải bởi Selenium Manager (Selenium >= 4.6).
 */
public class SetupBrowser {

    public static WebDriver createDriver(String browserName) {
        String browser = (browserName == null || browserName.isEmpty())
                ? PropertiesHelper.getValue("browser") : browserName;
        boolean headless = PropertiesHelper.getBoolean("headless");
        LogUtils.info("Khởi tạo browser: " + browser + " | headless: " + headless);

        WebDriver driver;
        switch (browser.trim().toLowerCase()) {
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless", "--width=1920", "--height=1080");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications", "--remote-allow-origins=*");
                if (headless) {
                    chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        if (!headless) {
            driver.manage().window().maximize();
        }
        // BT7: bỏ ImplicitWait, chỉ dùng Explicit wait trong WebUI
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PropertiesHelper.getInt("page.load.timeout", 40)));
        return driver;
    }
}
