package com.anhtester.mobile.keywords;

import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.reports.ExtentTestManager;
import com.anhtester.utils.LogUtils;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * Mobile AOS: thư viện keyword cho app Android (Appium UiAutomator2).
 * - Chỉ dùng Explicit wait.
 * - Mỗi hàm ghi log (Log4j2) + Extent + Allure step.
 */
public class MobileUI {

    private MobileUI() {
    }

    public static AndroidDriver driver() {
        return (AndroidDriver) DriverManager.getDriver();
    }

    private static WebDriverWait getWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(PropertiesHelper.getInt("mobile.explicit.wait", 20)),
                Duration.ofMillis(300));
    }

    public static void logConsole(String message) {
        LogUtils.info(message);
        ExtentTestManager.logMessage(Status.INFO, message);
    }

    //================ Locator helpers ================

    public static By byText(String text) {
        return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")");
    }

    public static By byTextContains(String text) {
        return AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
    }

    public static By byId(String resourceId) {
        String appPackage = PropertiesHelper.getValue("android.app.package");
        String id = resourceId.contains(":id/") || appPackage == null ? resourceId : appPackage + ":id/" + resourceId;
        return AppiumBy.id(id);
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

    public static boolean isElementDisplayed(By by) {
        List<WebElement> list = driver().findElements(by);
        return !list.isEmpty() && list.get(0).isDisplayed();
    }

    //================ Actions ================

    @Step("Tap element: {0}")
    public static void tap(By by) {
        waitForElementClickable(by).click();
        logConsole("Tap element: " + by);
    }

    @Step("Tap text: {0}")
    public static void tapText(String text) {
        tap(byText(text));
    }

    @Step("Set text on element {0}: {1}")
    public static void setText(By by, String value) {
        WebElement element = waitForElementVisible(by);
        element.clear();
        element.sendKeys(value);
        logConsole("Set text '" + value + "' on element: " + by);
    }

    @Step("Get text of element: {0}")
    public static String getText(By by) {
        String text = waitForElementVisible(by).getText();
        logConsole("Get text of element " + by + ": " + text);
        return text;
    }

    public static String getAttribute(By by, String attribute) {
        return waitForElementVisible(by).getAttribute(attribute);
    }

    public static boolean isChecked(By by) {
        return Boolean.parseBoolean(getAttribute(by, "checked"));
    }

    @Step("Hide keyboard")
    public static void hideKeyboard() {
        try {
            if (driver().isKeyboardShown()) {
                driver().hideKeyboard();
            }
        } catch (Exception e) {
            LogUtils.warn("Không ẩn được bàn phím: " + e.getMessage());
        }
    }

    @Step("Press BACK")
    public static void pressBack() {
        driver().pressKey(new KeyEvent(AndroidKey.BACK));
        logConsole("Press BACK");
    }

    //================ Gesture (W3C Actions) ================

    @Step("Scroll to text: {0}")
    public static WebElement scrollToText(String text) {
        By by = AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0))"
                        + ".scrollIntoView(new UiSelector().text(\"" + text + "\"))");
        WebElement element = waitForElementVisible(by);
        logConsole("Scroll to text: " + text);
        return element;
    }

    @Step("Scroll to text and tap: {0}")
    public static void scrollAndTapText(String text) {
        scrollToText(text);
        tapText(text);
    }

    public static void swipe(Point start, Point end, Duration duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.getX(), start.getY()))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.getX(), end.getY()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(Collections.singletonList(swipe));
    }

    @Step("Swipe up")
    public static void swipeUp() {
        Dimension size = driver().manage().window().getSize();
        int x = size.getWidth() / 2;
        swipe(new Point(x, (int) (size.getHeight() * 0.75)), new Point(x, (int) (size.getHeight() * 0.25)),
                Duration.ofMillis(600));
        logConsole("Swipe up");
    }

    @Step("Swipe down")
    public static void swipeDown() {
        Dimension size = driver().manage().window().getSize();
        int x = size.getWidth() / 2;
        swipe(new Point(x, (int) (size.getHeight() * 0.25)), new Point(x, (int) (size.getHeight() * 0.75)),
                Duration.ofMillis(600));
        logConsole("Swipe down");
    }

    @Step("Long press element: {0}")
    public static void longPress(By by) {
        WebElement element = waitForElementVisible(by);
        Point center = new Point(element.getRect().getX() + element.getRect().getWidth() / 2,
                element.getRect().getY() + element.getRect().getHeight() / 2);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence press = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), center.getX(), center.getY()))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(1500), PointerInput.Origin.viewport(), center.getX(), center.getY()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(Collections.singletonList(press));
        logConsole("Long press element: " + by);
    }

    //================ App ================

    @Step("Restart app")
    public static void restartApp() {
        String appPackage = PropertiesHelper.getValue("android.app.package");
        driver().terminateApp(appPackage);
        driver().activateApp(appPackage);
        logConsole("Restart app: " + appPackage);
    }

    //================ Verify ================

    @Step("Verify element text {0} equals: {1}")
    public static void verifyText(By by, String expected) {
        Assert.assertEquals(getText(by), expected, "Text không đúng tại element " + by);
    }

    @Step("Verify element displayed: {0}")
    public static void verifyElementDisplayed(By by) {
        Assert.assertNotNull(waitForElementVisible(by), "Element không hiển thị: " + by);
    }
}
