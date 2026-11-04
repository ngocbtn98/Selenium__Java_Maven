package ecommerce_basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;

import static ecommerce_basic.LocatorsCMS.*;

/**
 * BÀI TẬP 1: Add Category (chạy bằng hàm main).
 * Chuẩn bị: upload sẵn vài hình bằng tay trên trang Uploaded Files, tên hình lưu ở biến IMAGE_NAME.
 */
public class BT1_AddCategory {
    static final String IMAGE_NAME = "anhtester";
    static final String CATEGORY_NAME = "Category BT1 " + System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Login
        driver.get(URL_LOGIN);
        List<WebElement> cookie = driver.findElements(By.xpath(buttonAcceptCookie));
        if (!cookie.isEmpty() && cookie.get(0).isDisplayed()) cookie.get(0).click();
        driver.findElement(By.xpath(inputEmail)).sendKeys(EMAIL);
        driver.findElement(By.xpath(inputPassword)).sendKeys(PASSWORD);
        driver.findElement(By.xpath(buttonLogin)).click();
        Thread.sleep(2000);

        // Menu Products > Category
        driver.findElement(By.xpath(menuProducts)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath(menuCategory)).click();
        Thread.sleep(1000);

        // Add New category
        driver.findElement(By.xpath(buttonAddNewCategory)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(inputName)).sendKeys(CATEGORY_NAME);
        driver.findElement(By.xpath(inputOrderLevel)).sendKeys("1");

        // Chọn Type: Physical
        driver.findElement(By.xpath(dropdownType)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@data-id='digital']/following-sibling::div//span[normalize-space()='Physical']")).click();

        // Chọn hình Banner: click Browse -> search tên hình -> click item -> Add Files
        driver.findElement(By.xpath(buttonBrowseBanner)).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath(inputSearchUploader)).sendKeys(IMAGE_NAME, Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.xpath(firstImageUploader)).click();
        driver.findElement(By.xpath(buttonAddFiles)).click();
        Thread.sleep(1000);

        // Chọn hình Icon
        driver.findElement(By.xpath(buttonBrowseIcon)).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath(inputSearchUploader)).clear();
        driver.findElement(By.xpath(inputSearchUploader)).sendKeys(IMAGE_NAME, Keys.ENTER);
        Thread.sleep(2000);
        driver.findElement(By.xpath(firstImageUploader)).click();
        driver.findElement(By.xpath(buttonAddFiles)).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath(inputMetaTitle)).sendKeys("Meta " + CATEGORY_NAME);
        driver.findElement(By.xpath(textareaMetaDescription)).sendKeys("Description for " + CATEGORY_NAME);
        driver.findElement(By.xpath(buttonSave)).click();
        Thread.sleep(2000);

        // Trở về trang list Category và search
        driver.findElement(By.xpath(menuCategory)).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath(inputSearch)).sendKeys(CATEGORY_NAME, Keys.ENTER);
        Thread.sleep(2000);

        // Get text item đầu tiên cột Name
        String actualName = driver.findElement(By.xpath(firstRowName)).getText().trim();
        System.out.println("Category name sau khi search: " + actualName);
        if (actualName.equals(CATEGORY_NAME)) {
            System.out.println("PASSED: Category được thêm đúng");
        } else {
            System.out.println("FAILED: Expected = " + CATEGORY_NAME + " | Actual = " + actualName);
        }

        driver.quit();
    }
}
