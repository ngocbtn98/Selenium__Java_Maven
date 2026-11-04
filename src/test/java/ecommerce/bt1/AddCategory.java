package ecommerce.bt1;

import ecommerce.LocatorsCMS;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * BÀI TẬP 1: Add Category trên https://cms.anhtester.com
 * LƯU Ý: Upload sẵn vài tấm hình bằng tay (Uploaded Files) trước khi chạy,
 *        rồi sửa IMAGE_NAME bên dưới cho đúng tên hình.
 */
public class AddCategory {
    static final String IMAGE_NAME = "category";

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String categoryName = "Category BT1 " + System.currentTimeMillis();

        try {
            // Login
            driver.get(LocatorsCMS.urlLogin);
            driver.findElement(By.xpath(LocatorsCMS.inputEmail)).sendKeys(LocatorsCMS.email);
            driver.findElement(By.xpath(LocatorsCMS.inputPassword)).sendKeys(LocatorsCMS.password);
            driver.findElement(By.xpath(LocatorsCMS.buttonLogin)).click();
            Thread.sleep(2000);

            // Products -> Category -> Add New category
            driver.findElement(By.xpath(LocatorsCMS.menuProducts)).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath(LocatorsCMS.menuCategory)).click();
            driver.findElement(By.xpath(LocatorsCMS.buttonAddNewCategory)).click();

            // Điền form
            driver.findElement(By.xpath(LocatorsCMS.inputCategoryName)).sendKeys(categoryName);
            driver.findElement(By.xpath(LocatorsCMS.inputOrderingNumber)).sendKeys("1");

            // Chọn Banner: Browse -> search tên hình -> click hình -> Add Files
            chooseImage(driver, LocatorsCMS.buttonBrowseBanner);
            // Chọn Icon
            chooseImage(driver, LocatorsCMS.buttonBrowseIcon);

            driver.findElement(By.xpath(LocatorsCMS.inputMetaTitle)).sendKeys(categoryName);
            driver.findElement(By.xpath(LocatorsCMS.textareaMetaDescription)).sendKeys("Meta description " + categoryName);

            driver.findElement(By.xpath(LocatorsCMS.buttonSave)).click();
            Thread.sleep(2000);

            // Trở về trang list Category và search
            driver.findElement(By.xpath(LocatorsCMS.menuCategory)).click();
            driver.findElement(By.xpath(LocatorsCMS.inputSearch)).sendKeys(categoryName, Keys.ENTER);
            Thread.sleep(2000);

            String actualName = driver.findElement(By.xpath(LocatorsCMS.firstRowName)).getText().trim();
            System.out.println("Expected: " + categoryName);
            System.out.println("Actual  : " + actualName);
            if (actualName.equals(categoryName)) {
                System.out.println("PASSED: Category được thêm đúng");
            } else {
                System.out.println("FAILED: Category không đúng");
            }
        } finally {
            driver.quit();
        }
    }

    static void chooseImage(WebDriver driver, String browseButtonXpath) throws InterruptedException {
        driver.findElement(By.xpath(browseButtonXpath)).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath(LocatorsCMS.inputSearchImage)).sendKeys(IMAGE_NAME);
        Thread.sleep(2000);
        driver.findElement(By.xpath(LocatorsCMS.firstImageItem)).click();
        driver.findElement(By.xpath(LocatorsCMS.buttonAddFiles)).click();
        Thread.sleep(1000);
    }
}
