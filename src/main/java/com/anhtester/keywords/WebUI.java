package com.anhtester.keywords;

import com.anhtester.constants.ConfigData;
import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.CaptureHelper;
import com.anhtester.reports.AllureManager;
import com.anhtester.reports.ExtentTestManager;
import com.anhtester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * BT7, BT8: Thư viện hàm dùng chung (keywords).
 * - Tất cả hàm đều có Explicit wait, không dùng sleep/ImplicitWait
 * - Mỗi hàm ghi log (Log4j2), Extent Report, Allure Step
 */
public class WebUI {

    private static int timeout() {
        return ConfigData.EXPLICIT_WAIT();
    }

    private static WebDriver driver() {
        return DriverManager.getDriver();
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(timeout()), Duration.ofMillis(300));
    }

    private static void logStep(String message) {
        LogUtils.info(message);
        ExtentTestManager.logMessage(message);
        if (ConfigData.SCREENSHOT_ALL_STEP()) {
            CaptureHelper.takeScreenshot(message);
        }
    }

    public static void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ================= Navigation =================

    @Step("Open URL: {0}")
    public static void openURL(String url) {
        driver().get(url);
        waitForPageLoaded();
        logStep("Open URL: " + url);
    }

    public static String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    public static String getPageTitle() {
        return driver().getTitle();
    }

    @Step("Refresh page")
    public static void refreshPage() {
        driver().navigate().refresh();
        waitForPageLoaded();
    }

    // ================= Wait =================

    /** Chờ trang load xong (document.readyState == complete và jQuery không còn active) */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(ConfigData.PAGE_LOAD_TIMEOUT()), Duration.ofMillis(300));
        JavascriptExecutor js = (JavascriptExecutor) driver();
        ExpectedCondition<Boolean> jsLoad = d -> "complete".equals(js.executeScript("return document.readyState").toString());
        ExpectedCondition<Boolean> jQueryLoad = d -> {
            try {
                Object active = js.executeScript("return (window.jQuery != null) ? jQuery.active : 0");
                return active == null || Long.parseLong(active.toString()) == 0;
            } catch (Exception e) {
                return true;
            }
        };
        try {
            wait.until(jsLoad);
            wait.until(jQueryLoad);
        } catch (Throwable error) {
            LogUtils.warn("Timeout waiting for page load (" + ConfigData.PAGE_LOAD_TIMEOUT() + "s)");
        }
    }

    public static WebElement waitForElementVisible(By by) {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for element visible: " + by);
            Assert.fail("Timeout waiting for element visible: " + by);
            return null;
        }
    }

    public static WebElement waitForElementClickable(By by) {
        try {
            return getWait().until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for element clickable: " + by);
            Assert.fail("Timeout waiting for element clickable: " + by);
            return null;
        }
    }

    public static WebElement waitForElementPresent(By by) {
        try {
            return getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not present: " + by);
            Assert.fail("Element not present: " + by);
            return null;
        }
    }

    public static boolean waitForElementInvisible(By by) {
        try {
            return getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.warn("Element still visible: " + by);
            return false;
        }
    }

    public static boolean waitForTextPresent(By by, String text) {
        try {
            return getWait().until(ExpectedConditions.textToBePresentInElementLocated(by, text));
        } catch (Throwable error) {
            LogUtils.warn("Text '" + text + "' not present in " + by);
            return false;
        }
    }

    // ================= Element actions =================

    public static WebElement getWebElement(By by) {
        return driver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return driver().findElements(by);
    }

    @Step("Click element {0}")
    public static void clickElement(By by) {
        waitForPageLoaded();
        WebElement element = waitForElementClickable(by);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            LogUtils.warn("Click bị chặn, thử click bằng JS: " + by);
            clickElementWithJs(by);
        }
        logStep("Click element: " + by);
    }

    @Step("Click element {0} by JS")
    public static void clickElementWithJs(By by) {
        WebElement element = waitForElementPresent(by);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", element);
        logStep("Click element by JS: " + by);
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String value) {
        waitForPageLoaded();
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(value);
        logStep("Set text '" + value + "' on element " + by);
    }

    @Step("Set text {1} on element {0} and press key {2}")
    public static void setTextAndKey(By by, String value, Keys key) {
        waitForPageLoaded();
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(value, key);
        logStep("Set text '" + value + "' and press " + key.name() + " on element " + by);
    }

    @Step("Clear text with Ctrl + A and Delete on element {0}")
    public static void clearTextWithKeys(By by) {
        WebElement element = waitForElementVisible(by);
        element.click();
        new Actions(driver()).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
        logStep("Clear text with Ctrl + A, Delete on element " + by);
    }

    @Step("Get text of element {0}")
    public static String getElementText(By by) {
        waitForPageLoaded();
        String text = waitForElementVisible(by).getText().trim();
        logStep("Get text of element " + by + " => " + text);
        return text;
    }

    public static String getElementAttribute(By by, String attribute) {
        String value = waitForElementPresent(by).getAttribute(attribute);
        logStep("Get attribute '" + attribute + "' of " + by + " => " + value);
        return value;
    }

    public static boolean checkElementExist(By by) {
        List<WebElement> list = driver().findElements(by);
        return !list.isEmpty();
    }

    public static boolean isElementDisplayed(By by) {
        try {
            return driver().findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Scroll to element {0}")
    public static void scrollToElement(By by) {
        WebElement element = waitForElementPresent(by);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    @Step("Hover on element {0}")
    public static void hoverElement(By by) {
        new Actions(driver()).moveToElement(waitForElementVisible(by)).perform();
    }

    @Step("Press key {0}")
    public static void pressKey(Keys key) {
        new Actions(driver()).sendKeys(key).perform();
    }

    // ================= Dropdown =================

    @Step("Select option by text {1} on {0}")
    public static void selectOptionByText(By by, String text) {
        new Select(waitForElementPresent(by)).selectByVisibleText(text);
        logStep("Select option '" + text + "' on " + by);
    }

    /**
     * Chọn giá trị trong dropdown bootstrap-select (có ô search).
     * @param dataId giá trị thuộc tính data-id của button dropdown
     */
    @Step("Select bootstrap dropdown {0} with value {1}")
    public static void selectBootstrapDropdown(String dataId, String value) {
        By button = By.xpath("//button[@data-id='" + dataId + "']");
        By search = By.xpath("//button[@data-id='" + dataId + "']/following-sibling::div//input[@type='search' or @type='text']");
        By option = By.xpath("//button[@data-id='" + dataId + "']/following-sibling::div//li[not(contains(@class,'hidden'))]//span[@class='text' and normalize-space()='" + value + "']");
        clickElement(button);
        if (checkElementExist(search)) {
            setText(search, value);
        }
        clickElement(option);
        logStep("Select '" + value + "' in dropdown " + dataId);
    }

    public static List<String> getAllSelectedOptions(By selectBy) {
        List<String> result = new ArrayList<>();
        for (WebElement option : new Select(waitForElementPresent(selectBy)).getAllSelectedOptions()) {
            result.add(option.getText().trim());
        }
        return result;
    }

    // ================= Frame / Window / Alert =================

    public static void switchToFrame(By by) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public static void switchToDefaultContent() {
        driver().switchTo().defaultContent();
    }

    public static void acceptAlert() {
        getWait().until(ExpectedConditions.alertIsPresent()).accept();
    }

    // ================= Upload =================

    /** Chọn file trong popup AIZ uploader: Browse -> search tên -> click item -> Add Files */
    @Step("Select image {1} from uploader")
    public static void selectImageFromUploader(By browseButton, String imageName) {
        By search = By.xpath("//input[@name='aiz-uploader-search']");
        By firstItem = By.xpath("(//div[contains(@class,'aiz-uploader-select')])[1]");
        By addFiles = By.xpath("//button[normalize-space()='Add Files']");
        clickElement(browseButton);
        setTextAndKey(search, imageName, Keys.ENTER);
        waitForPageLoaded();
        clickElement(firstItem);
        clickElement(addFiles);
        waitForElementInvisible(addFiles);
    }

    // ================= Table (BT9) =================

    /** Lấy text tất cả các ô của 1 cột trong table */
    public static List<String> getColumnValues(By rowsLocator, int columnIndex) {
        List<String> values = new ArrayList<>();
        List<WebElement> rows = getWebElements(rowsLocator);
        for (int i = 1; i <= rows.size(); i++) {
            By cell = By.xpath("(" + toXpath(rowsLocator) + ")[" + i + "]/td[" + columnIndex + "]");
            if (checkElementExist(cell)) {
                values.add(getWebElement(cell).getText().trim());
            }
        }
        return values;
    }

    /** BT9: Kiểm tra tất cả dòng của cột sau khi search đều chứa giá trị search */
    @Step("Verify search table column {1} contains {2}")
    public static void checkContainsSearchTableByColumn(By rowsLocator, int columnIndex, String value) {
        waitForPageLoaded();
        List<String> values = getColumnValues(rowsLocator, columnIndex);
        Assert.assertFalse(values.isEmpty(), "Không có dữ liệu nào trong table khi search: " + value);
        for (int i = 0; i < values.size(); i++) {
            LogUtils.info("Row " + (i + 1) + ": " + values.get(i));
            Assert.assertTrue(values.get(i).toLowerCase().contains(value.toLowerCase()),
                    "Row " + (i + 1) + " không chứa giá trị search. Actual: " + values.get(i));
        }
        logStep("Verify " + values.size() + " row(s) contain '" + value + "'");
    }

    private static String toXpath(By by) {
        String s = by.toString();
        return s.startsWith("By.xpath: ") ? s.substring("By.xpath: ".length()) : s;
    }

    // ================= Verify =================

    @Step("Verify equals: {0} == {1}")
    public static void verifyEquals(Object actual, Object expected, String message) {
        LogUtils.info("Verify equals: actual = " + actual + " | expected = " + expected);
        ExtentTestManager.logMessage("Verify equals: actual = " + actual + " | expected = " + expected);
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Verify contains: {0} contains {1}")
    public static void verifyContains(String actual, String expected, String message) {
        LogUtils.info("Verify contains: actual = " + actual + " | expected = " + expected);
        ExtentTestManager.logMessage("Verify contains: actual = " + actual + " | expected = " + expected);
        Assert.assertTrue(actual != null && actual.contains(expected), message + " | actual: " + actual + " | expected: " + expected);
    }

    @Step("Verify true: {1}")
    public static void verifyTrue(boolean condition, String message) {
        LogUtils.info("Verify true: " + message + " => " + condition);
        Assert.assertTrue(condition, message);
    }

    @Step("Verify element visible {0}")
    public static void verifyElementVisible(By by, String message) {
        Assert.assertTrue(waitForElementVisible(by).isDisplayed(), message);
    }

    /** Chụp ảnh màn hình 1 trang (BT12) và đính kèm Allure */
    public static void takeScreenshotPage(String pageName) {
        CaptureHelper.takeScreenshot(pageName);
        AllureManager.takeScreenshot(pageName);
    }
}
