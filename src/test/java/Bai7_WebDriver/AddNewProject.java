package Bai7_WebDriver;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * BT WebDriver: Chạy auto phần New Project, sử dụng các hàm của WebDriver:
 * get, getTitle, getCurrentUrl, navigate().to/back/refresh, switchTo().frame, manage()...
 * https://crm.anhtester.com/admin/projects/project
 */
public class AddNewProject {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        String projectName = "Project Anh Tester " + System.currentTimeMillis();

        // Login
        driver.get(LocatorsCRM.url);
        System.out.println("Title: " + driver.getTitle());
        driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys(LocatorsCRM.email);
        driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys(LocatorsCRM.password);
        driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();
        Thread.sleep(1000);
        System.out.println("Current URL sau login: " + driver.getCurrentUrl());

        // Điều hướng bằng navigate()
        driver.navigate().to("https://crm.anhtester.com/admin/projects");
        System.out.println("Projects page displayed: " + driver.findElement(By.xpath(LocatorsCRM.headerProjectsSummary)).isDisplayed());
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();

        // Mở form New Project
        driver.get("https://crm.anhtester.com/admin/projects/project");
        System.out.println("Title trang New Project: " + driver.getTitle());

        driver.findElement(By.xpath(LocatorsCRM.inputProjectName)).sendKeys(projectName);

        // Customer (ajax search)
        driver.findElement(By.xpath(LocatorsCRM.dropdownCustomer)).click();
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCustomerInProject)).sendKeys("Anh Tester");
        Thread.sleep(2000);
        driver.findElement(By.xpath(LocatorsCRM.inputSearchCustomerInProject)).sendKeys(Keys.ENTER);

        // Billing type
        driver.findElement(By.xpath(LocatorsCRM.dropdownBillingType)).click();
        driver.findElement(By.xpath("//button[@data-id='billing_type']/following-sibling::div//span[normalize-space()='Fixed Rate']")).click();
        driver.findElement(By.xpath(LocatorsCRM.inputTotalRate)).sendKeys("1000");

        // Status
        driver.findElement(By.xpath(LocatorsCRM.dropdownStatus)).click();
        driver.findElement(By.xpath("//button[@data-id='status']/following-sibling::div//span[normalize-space()='In Progress']")).click();

        driver.findElement(By.xpath(LocatorsCRM.inputEstimatedHours)).sendKeys("40");

        // Dates
        driver.findElement(By.xpath(LocatorsCRM.inputStartDate)).clear();
        driver.findElement(By.xpath(LocatorsCRM.inputStartDate)).sendKeys("01-01-2025");
        driver.findElement(By.xpath(LocatorsCRM.inputDeadline)).sendKeys("31-12-2025", Keys.ESCAPE);

        // Tags
        driver.findElement(By.xpath(LocatorsCRM.inputTags)).sendKeys("selenium", Keys.ENTER);

        // Description nằm trong iframe TinyMCE => switchTo().frame
        driver.switchTo().frame(driver.findElement(By.xpath(LocatorsCRM.iframeDescription)));
        driver.findElement(By.xpath(LocatorsCRM.bodyDescription)).sendKeys("Project tạo bằng Selenium WebDriver");
        driver.switchTo().defaultContent();

        // Save
        driver.findElement(By.xpath(LocatorsCRM.buttonSaveProject)).click();
        Thread.sleep(2000);
        System.out.println("URL sau khi save: " + driver.getCurrentUrl());

        // Kiểm tra trong danh sách
        driver.navigate().to("https://crm.anhtester.com/admin/projects");
        driver.findElement(By.xpath(LocatorsCRM.inputSearchProject)).sendKeys(projectName);
        Thread.sleep(2000);
        String actual = driver.findElement(By.xpath(LocatorsCRM.firstRowProjectName)).getText();
        System.out.println(actual.equals(projectName) ? "PASSED: Add project thành công" : "FAILED: actual = " + actual);

        driver.quit();
    }
}
