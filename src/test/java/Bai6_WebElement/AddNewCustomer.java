package Bai6_WebElement;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * BT WebElement: Chạy auto phần Add New Customer.
 * https://crm.anhtester.com/admin/clients (admin@example.com/123456)
 */
public class AddNewCustomer {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String companyName = "Anh Tester Company " + System.currentTimeMillis();

        // Login
        driver.get(LocatorsCRM.url);
        driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys(LocatorsCRM.email);
        driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys(LocatorsCRM.password);
        driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();
        Thread.sleep(1000);

        // Mở trang Customers
        driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).click();
        System.out.println("Customers page displayed: " + driver.findElement(By.xpath(LocatorsCRM.headerCustomersPage)).isDisplayed());

        // Click New Customer
        driver.findElement(By.xpath(LocatorsCRM.buttonNewCustomer)).click();
        Thread.sleep(1000);

        // Điền form
        driver.findElement(By.xpath(LocatorsCRM.inputCompany)).sendKeys(companyName);
        driver.findElement(By.xpath(LocatorsCRM.inputVatNumber)).sendKeys("10");
        driver.findElement(By.xpath(LocatorsCRM.inputPhone)).sendKeys("0987654321");
        driver.findElement(By.xpath(LocatorsCRM.inputWebsite)).sendKeys("https://anhtester.com");

        // Groups (bootstrap-select: click -> search -> Enter)
        driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchGroups)).sendKeys("VIP");
        Thread.sleep(500);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchGroups)).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click(); // đóng dropdown multi-select

        // Currency
        driver.findElement(By.xpath(LocatorsCRM.dropdownCurrency)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCurrency)).sendKeys("USD", Keys.ENTER);

        // Language
        driver.findElement(By.xpath(LocatorsCRM.dropdownLanguage)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//button[@data-id='default_language']/following-sibling::div//span[normalize-space()='Vietnamese']")).click();

        driver.findElement(By.xpath(LocatorsCRM.textareaAddress)).sendKeys("123 Cầu Giấy");
        driver.findElement(By.xpath(LocatorsCRM.inputCity)).sendKeys("Hà Nội");
        driver.findElement(By.xpath(LocatorsCRM.inputState)).sendKeys("Hà Nội");
        driver.findElement(By.xpath(LocatorsCRM.inputZipCode)).sendKeys("100000");

        // Country
        driver.findElement(By.xpath(LocatorsCRM.dropdownCountry)).click();
        Thread.sleep(500);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCountry)).sendKeys("Vietnam");
        Thread.sleep(500);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCountry)).sendKeys(Keys.ENTER);

        // Save
        driver.findElement(By.xpath(LocatorsCRM.buttonSaveCustomer)).click();
        Thread.sleep(2000);

        // Kiểm tra lại customer vừa tạo trong danh sách
        driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).click();
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCustomer)).sendKeys(companyName);
        Thread.sleep(2000);
        String actualName = driver.findElement(By.xpath(LocatorsCRM.firstRowCustomerName)).getText();
        System.out.println("Customer vừa tạo: " + actualName);
        System.out.println(actualName.equals(companyName) ? "PASSED: Add customer thành công" : "FAILED: Không tìm thấy customer vừa tạo");

        driver.quit();
    }
}
