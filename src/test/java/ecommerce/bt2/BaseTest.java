package ecommerce.bt2;

import ecommerce.LocatorsCMS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

/**
 * BÀI TẬP 2: Tách setup driver + Login ra khỏi test case bằng annotation TestNG.
 * - @BeforeMethod: khởi tạo driver và Login trước MỖI test case
 * - @AfterMethod: đóng driver
 * - Thay Thread.sleep bằng explicit wait cho ổn định
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        login();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void login() {
        driver.get(LocatorsCMS.urlLogin);
        setText(LocatorsCMS.inputEmail, LocatorsCMS.email);
        setText(LocatorsCMS.inputPassword, LocatorsCMS.password);
        clickElement(LocatorsCMS.buttonLogin);
        waitForElementVisible(LocatorsCMS.menuProducts);
    }

    //===== Hàm hỗ trợ có explicit wait =====
    protected WebElement waitForElementVisible(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    protected void clickElement(String xpath) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        element.click();
    }

    protected void setText(String xpath, CharSequence... text) {
        WebElement element = waitForElementVisible(xpath);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(String xpath) {
        return waitForElementVisible(xpath).getText().trim();
    }

    /** Click menu con của Products, chỉ mở menu Products nếu menu con đang bị ẩn */
    protected void openProductsSubMenu(String subMenuXpath) {
        List<WebElement> subMenu = driver.findElements(By.xpath(subMenuXpath));
        if (subMenu.isEmpty() || !subMenu.get(0).isDisplayed()) {
            clickElement(LocatorsCMS.menuProducts);
        }
        clickElement(subMenuXpath);
    }

    /** Chờ bảng load lại sau khi search (dòng đầu tiên chứa text mong muốn) */
    protected void waitForTextInElement(String xpath, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), text));
    }

    protected void chooseImage(String browseButtonXpath, String imageName) {
        clickElement(browseButtonXpath);
        setText(LocatorsCMS.inputSearchImage, imageName);
        // chờ kết quả search hình
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LocatorsCMS.firstImageItem)));
        clickElement(LocatorsCMS.firstImageItem);
        clickElement(LocatorsCMS.buttonAddFiles);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LocatorsCMS.buttonAddFiles)));
    }
}
