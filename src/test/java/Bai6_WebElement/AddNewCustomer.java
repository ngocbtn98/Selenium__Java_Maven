package Bai6_WebElement;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * BT WebElement: chạy auto phần Add New Customer - https://crm.anhtester.com/admin/clients
 * Dùng các locator từ BT-Locators (LocatorsCRM).
 */
public class AddNewCustomer {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String companyName = "Anh Tester Customer " + System.currentTimeMillis();

        try {
            // Login
            driver.get(LocatorsCRM.url);
            driver.findElement(By.xpath(LocatorsCRM.inputEmail)).clear();
            driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys(LocatorsCRM.email);
            driver.findElement(By.xpath(LocatorsCRM.inputPassword)).clear();
            driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys(LocatorsCRM.password);
            driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();

            System.out.println("Menu Customers is displayed: "
                    + driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).isDisplayed());

            // Vào Customers -> New Customer
            driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).click();
            driver.findElement(By.xpath(LocatorsCRM.buttonNewCustomer)).click();

            // Điền form
            driver.findElement(By.xpath(LocatorsCRM.inputCompany)).sendKeys(companyName);
            driver.findElement(By.xpath(LocatorsCRM.inputVatNumber)).sendKeys("10");
            driver.findElement(By.xpath(LocatorsCRM.inputPhone)).sendKeys("0901234567");
            driver.findElement(By.xpath(LocatorsCRM.inputWebsite)).sendKeys("https://anhtester.com");

            // Groups (multi select) - search và chọn
            driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click();
            Thread.sleep(500);
            WebElement searchGroups = driver.findElement(By.xpath(LocatorsCRM.inputSearchGroups));
            searchGroups.sendKeys("VIP");
            Thread.sleep(500);
            searchGroups.sendKeys(Keys.ENTER);
            driver.findElement(By.xpath(LocatorsCRM.dropdownGroups)).click(); // đóng dropdown

            // Currency
            driver.findElement(By.xpath(LocatorsCRM.dropdownCurrency)).click();
            Thread.sleep(500);
            WebElement searchCurrency = driver.findElement(By.xpath(LocatorsCRM.inputSearchCurrency));
            searchCurrency.sendKeys("USD");
            Thread.sleep(500);
            searchCurrency.sendKeys(Keys.ENTER);

            // Language
            driver.findElement(By.xpath(LocatorsCRM.dropdownLanguage)).click();
            Thread.sleep(500);
            driver.findElement(By.xpath(LocatorsCRM.optionOfDropdown(LocatorsCRM.dropdownLanguage, "Vietnamese"))).click();

            driver.findElement(By.xpath(LocatorsCRM.textareaAddress)).sendKeys("123 Lê Lợi");
            driver.findElement(By.xpath(LocatorsCRM.inputCity)).sendKeys("Hồ Chí Minh");
            driver.findElement(By.xpath(LocatorsCRM.inputState)).sendKeys("Quận 1");
            driver.findElement(By.xpath(LocatorsCRM.inputZipCode)).sendKeys("700000");

            // Country
            driver.findElement(By.xpath(LocatorsCRM.dropdownCountry)).click();
            Thread.sleep(500);
            WebElement searchCountry = driver.findElement(By.xpath(LocatorsCRM.inputSearchCountry));
            searchCountry.sendKeys("Vietnam");
            Thread.sleep(500);
            searchCountry.sendKeys(Keys.ENTER);

            // Save
            driver.findElement(By.xpath(LocatorsCRM.buttonSaveCustomer)).click();
            Thread.sleep(2000);

            // Kiểm tra lại ở trang danh sách
            driver.findElement(By.xpath(LocatorsCRM.menuCustomers)).click();
            driver.findElement(By.xpath(LocatorsCRM.inputSearchCustomer)).sendKeys(companyName);
            Thread.sleep(2000);
            String actualName = driver.findElement(By.xpath(LocatorsCRM.firstRowCustomerName)).getText();
            System.out.println("Customer vừa thêm: " + actualName);
            System.out.println(actualName.equals(companyName) ? "PASSED: Add New Customer" : "FAILED: Add New Customer");
        } finally {
            driver.quit();
        }
    }
}
