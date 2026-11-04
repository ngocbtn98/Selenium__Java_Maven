package ecommerce_basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

import static ecommerce_basic.LocatorsCMS.*;

/**
 * BÀI TẬP 2: Phần setup dùng chung cho BT2, BT3, BT4 (không chứa @Test để class con không bị chạy lặp test).
 * - Setup driver tách riêng (@BeforeMethod)
 * - Login chạy trước mỗi test case (@BeforeMethod)
 * - Optimize: dùng explicit wait thay cho sleep
 * - Chạy bằng suite: src/test/resources/suites/BT2_Suite.xml
 */
public abstract class BT2_BaseSetup {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String IMAGE_NAME = "anhtester";

    @BeforeMethod(alwaysRun = true)
    public void createDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        login();
    }

    public void login() {
        driver.get(URL_LOGIN);
        List<WebElement> cookie = driver.findElements(By.xpath(buttonAcceptCookie));
        if (!cookie.isEmpty() && cookie.get(0).isDisplayed()) cookie.get(0).click();
        type(inputEmail, EMAIL);
        type(inputPassword, PASSWORD);
        click(buttonLogin);
        waitVisible(menuDashboard);
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        if (driver != null) driver.quit();
    }

    // ===== Hàm hỗ trợ =====
    protected WebElement waitVisible(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    protected void click(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    protected void type(String xpath, CharSequence... text) {
        WebElement element = waitVisible(xpath);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(String xpath) {
        return waitVisible(xpath).getText().trim();
    }

    protected void selectImage(String browseButton) {
        click(browseButton);
        type(inputSearchUploader, IMAGE_NAME, Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(inputSearchUploader), IMAGE_NAME));
        click(firstImageUploader);
        click(buttonAddFiles);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(buttonAddFiles)));
    }

    protected void openCategoryPage() {
        click(menuProducts);
        click(menuCategory);
        waitVisible(headerAllCategories);
    }

    protected void addCategory(String categoryName) {
        openCategoryPage();
        click(buttonAddNewCategory);
        waitVisible(headerCategoryInformation);
        type(inputName, categoryName);
        type(inputOrderLevel, "1");
        click(dropdownType);
        click("//button[@data-id='digital']/following-sibling::div//span[normalize-space()='Physical']");
        selectImage(buttonBrowseBanner);
        selectImage(buttonBrowseIcon);
        type(inputMetaTitle, "Meta " + categoryName);
        type(textareaMetaDescription, "Description for " + categoryName);
        click(buttonSave);
    }

    protected void searchCategory(String categoryName) {
        openCategoryPage();
        type(inputSearch, categoryName, Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(firstRowName), categoryName));
    }
}
