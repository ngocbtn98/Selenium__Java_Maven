package com.anhtester.keywords;

import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.reports.AllureManager;
import com.anhtester.reports.ExtentTestManager;
import com.anhtester.utils.LogUtils;
import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * BÀI TẬP 7 + 8: Thư viện hàm chung (Common Library / Keywords).
 * - Tất cả hàm đều dùng Explicit wait, không dùng sleep / ImplicitWait.
 * - BÀI TẬP 9: các hàm xử lý Web Table.
 * - Mỗi hàm đều ghi log (Log4j2) + Extent + Allure step.
 */
public class WebUI {

    private static int timeout() {
        return PropertiesHelper.getInt("explicit.wait", 15);
    }

    private static WebDriver driver() {
        return DriverManager.getDriver();
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(timeout()), Duration.ofMillis(300));
    }

    public static void logConsole(String message) {
        LogUtils.info(message);
        ExtentTestManager.logMessage(Status.INFO, message);
    }

    //================ Browser ================

    @Step("Open URL: {0}")
    public static void openURL(String url) {
        driver().get(url);
        waitForPageLoaded();
        logConsole("Open URL: " + url);
    }

    public static String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    @Step("Wait for page loaded")
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(PropertiesHelper.getInt("page.load.timeout", 40)));
        JavascriptExecutor js = (JavascriptExecutor) driver();

        ExpectedCondition<Boolean> documentReady = d ->
                "complete".equals(js.executeScript("return document.readyState").toString());
        ExpectedCondition<Boolean> jQueryLoaded = d -> {
            Object result = js.executeScript("return (typeof jQuery === 'undefined') ? 0 : jQuery.active");
            return result == null || Long.parseLong(result.toString()) == 0;
        };
        try {
            wait.until(documentReady);
            wait.until(jQueryLoaded);
        } catch (Throwable e) {
            LogUtils.error("Timeout chờ page load: " + e.getMessage());
            Assert.fail("Timeout waiting for page load.");
        }
    }

    @Step("Wait for URL contains: {0}")
    public static void waitForUrlContains(String text) {
        getWait().until(ExpectedConditions.urlContains(text));
    }

    //================ Wait ================

    public static WebElement waitForElementVisible(By by) {
        try {
            return getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable e) {
            LogUtils.error("Element không hiển thị: " + by);
            Assert.fail("Timeout waiting for the element visible. " + by);
            return null;
        }
    }

    public static WebElement waitForElementClickable(By by) {
        try {
            return getWait().until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable e) {
            LogUtils.error("Element không click được: " + by);
            Assert.fail("Timeout waiting for the element to be clickable. " + by);
            return null;
        }
    }

    public static WebElement waitForElementPresent(By by) {
        try {
            return getWait().until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable e) {
            LogUtils.error("Element không tồn tại: " + by);
            Assert.fail("Element not present. " + by);
            return null;
        }
    }

    public static boolean waitForElementInvisible(By by) {
        try {
            return getWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Throwable e) {
            LogUtils.warn("Element vẫn hiển thị: " + by);
            return false;
        }
    }

    public static boolean waitForTextInElement(By by, String text) {
        try {
            return getWait().until(ExpectedConditions.textToBePresentInElementLocated(by, text));
        } catch (Throwable e) {
            LogUtils.warn("Không thấy text '" + text + "' trong element " + by);
            return false;
        }
    }

    //================ Element actions ================

    @Step("Click element {0}")
    public static void clickElement(By by) {
        // Thử lại khi element bị render lại (StaleElementReferenceException) sau ajax
        for (int attempt = 1; ; attempt++) {
            WebElement element = waitForElementClickable(by);
            try {
                scrollToElement(element);
                try {
                    element.click();
                } catch (StaleElementReferenceException e) {
                    throw e;
                } catch (Exception e) {
                    // Bị che bởi overlay/toast -> click bằng JS
                    ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
                }
                break;
            } catch (StaleElementReferenceException e) {
                if (attempt >= 3) {
                    throw e;
                }
                LogUtils.warn("Element bị stale, thử click lại lần " + (attempt + 1) + ": " + by);
            }
        }
        logConsole("Click element " + by);
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String value) {
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(value);
        logConsole("Set text '" + value + "' on element " + by);
    }

    @Step("Set text {1} and press key on element {0}")
    public static void setTextAndKey(By by, String value, Keys key) {
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(value, key);
        logConsole("Set text '" + value + "' and press key " + key.name() + " on element " + by);
    }

    @Step("Clear text on element {0}")
    public static void clearText(By by) {
        WebElement element = waitForElementVisible(by);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        logConsole("Clear text on element " + by);
    }

    @Step("Get text of element {0}")
    public static String getElementText(By by) {
        String text = waitForElementVisible(by).getText().trim();
        logConsole("Get text of element " + by + ": " + text);
        return text;
    }

    public static String getAttributeElement(By by, String attributeName) {
        String value = waitForElementPresent(by).getAttribute(attributeName);
        logConsole("Get attribute '" + attributeName + "' of " + by + ": " + value);
        return value;
    }

    public static boolean checkElementExist(By by) {
        List<WebElement> elements = driver().findElements(by);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public static void scrollToElement(By by) {
        scrollToElement(waitForElementPresent(by));
    }

    @Step("Hover element {0}")
    public static void hoverElement(By by) {
        new Actions(driver()).moveToElement(waitForElementVisible(by)).perform();
        logConsole("Hover element " + by);
    }

    @Step("Press key {0}")
    public static void pressKey(Keys key) {
        new Actions(driver()).sendKeys(key).perform();
        logConsole("Press key " + key.name());
    }

    /** BT8: gõ phím vào element đang focus bằng Actions (dùng cho editor, tagify...) */
    @Step("Send keys {0} by Actions")
    public static void sendKeysWithActions(String text) {
        new Actions(driver()).sendKeys(text).perform();
        logConsole("Send keys by Actions: " + text);
    }

    /** BT8: set text vào element đầu tiên đang hiển thị (form có nhiều input cùng name bị ẩn) */
    @Step("Set text {1} on first visible element {0}")
    public static void setTextFirstVisible(By by, String value) {
        waitForElementPresent(by);
        WebElement element = driver().findElements(by).stream()
                .filter(WebElement::isDisplayed).findFirst().orElse(null);
        if (element == null) {
            LogUtils.warn("Không có element hiển thị: " + by);
            return;
        }
        scrollToElement(element);
        element.clear();
        element.sendKeys(value);
        logConsole("Set text '" + value + "' on first visible element " + by);
    }

    /** BT8: đếm số element */
    public static int getElementCount(By by) {
        return driver().findElements(by).size();
    }

    /**
     * Chọn option trong dropdown bootstrap-select có ô search:
     * click dropdown -> nhập text vào ô search -> chờ option hiện -> Enter.
     */
    @Step("Select option {2} in searchable dropdown {0}")
    public static void selectSearchableDropdown(By dropdownButton, By searchInput, String optionText) {
        clickElement(dropdownButton);
        WebElement input = waitForElementVisible(searchInput);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        input.sendKeys(optionText);
        // option khớp text hiện ra trong dropdown đang mở
        waitForElementVisible(By.xpath("//div[contains(@class,'dropdown-menu') and contains(@class,'show')]"
                + "//li[not(contains(@class,'hidden'))]//span[@class='text' and normalize-space()='" + optionText + "']"));
        input.sendKeys(Keys.ENTER);
        logConsole("Select option '" + optionText + "' in dropdown " + dropdownButton);
    }

    //================ Verify ================

    @Step("Verify equals: {0} == {1}")
    public static void verifyEquals(Object actual, Object expected, String message) {
        logConsole("Verify equals: '" + actual + "' == '" + expected + "'");
        Assert.assertEquals(actual, expected, message);
    }

    @Step("Verify contains: {0} contains {1}")
    public static void verifyContains(String actual, String expected, String message) {
        logConsole("Verify contains: '" + actual + "' contains '" + expected + "'");
        Assert.assertTrue(actual != null && actual.contains(expected), message + " | actual: " + actual);
    }

    @Step("Verify true: {1}")
    public static void verifyTrue(boolean condition, String message) {
        logConsole("Verify true: " + message);
        Assert.assertTrue(condition, message);
    }

    //================ BÀI TẬP 9: Web Table ================

    /** Lấy toàn bộ giá trị của 1 cột (columnIndex bắt đầu từ 1) trong bảng */
    public static List<String> getTableColumnValues(By tableRows, int columnIndex) {
        waitForElementVisible(tableRows);
        List<WebElement> rows = driver().findElements(tableRows);
        List<String> values = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath("./td[" + columnIndex + "]"));
            if (!cells.isEmpty()) {
                values.add(cells.get(0).getText().trim());
            }
        }
        logConsole("Giá trị cột " + columnIndex + ": " + values);
        return values;
    }

    /** Kiểm tra tất cả dòng của cột chứa giá trị search (không phân biệt hoa thường) */
    @Step("Check search table by column {1} contains {2}")
    public static void checkSearchTableByColumn(By tableRows, int columnIndex, String searchValue) {
        List<String> values = getTableColumnValues(tableRows, columnIndex);
        Assert.assertFalse(values.isEmpty(), "Bảng không có dữ liệu sau khi search: " + searchValue);
        for (int i = 0; i < values.size(); i++) {
            Assert.assertTrue(values.get(i).toLowerCase().contains(searchValue.toLowerCase()),
                    "Dòng " + (i + 1) + " không chứa giá trị search '" + searchValue + "': " + values.get(i));
        }
        logConsole("Tất cả " + values.size() + " dòng đều chứa '" + searchValue + "'");
    }

    /** Lấy text 1 ô trong bảng theo dòng, cột (bắt đầu từ 1) */
    public static String getTableCellText(By tableRows, int rowIndex, int columnIndex) {
        waitForElementVisible(tableRows);
        List<WebElement> rows = driver().findElements(tableRows);
        if (rowIndex > rows.size()) {
            Assert.fail("Bảng chỉ có " + rows.size() + " dòng, không có dòng " + rowIndex);
        }
        return rows.get(rowIndex - 1).findElement(By.xpath("./td[" + columnIndex + "]")).getText().trim();
    }

    //================ Attach ================

    public static void attachScreenshotToReport(String message) {
        AllureManager.takeScreenshotPNG();
        ExtentTestManager.logMessage(Status.INFO, message);
    }
}
