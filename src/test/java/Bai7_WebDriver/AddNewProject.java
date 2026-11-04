package Bai7_WebDriver;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * BT WebDriver: chạy auto phần New Project - https://crm.anhtester.com/admin/projects/project
 * Vận dụng các hàm WebDriver: get, navigate, getTitle, getCurrentUrl, switchTo().frame, quit...
 */
public class AddNewProject {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        String projectName = "Anh Tester Project " + System.currentTimeMillis();

        try {
            driver.get(LocatorsCRM.url);
            System.out.println("Title trang Login: " + driver.getTitle());

            driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys(LocatorsCRM.email);
            driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys(LocatorsCRM.password);
            driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();
            System.out.println("URL sau khi login: " + driver.getCurrentUrl());

            // Vào thẳng trang New Project bằng navigate
            driver.findElement(By.xpath(LocatorsCRM.menuProjects)).click();
            System.out.println("URL trang Projects: " + driver.getCurrentUrl());
            driver.navigate().to("https://crm.anhtester.com/admin/projects/project");
            System.out.println("Title trang New Project: " + driver.getTitle());

            driver.findElement(By.xpath(LocatorsCRM.inputProjectName)).sendKeys(projectName);

            // Customer (ajax search)
            driver.findElement(By.xpath(LocatorsCRM.dropdownCustomer)).click();
            WebElement searchCustomer = driver.findElement(By.xpath(LocatorsCRM.inputSearchCustomerProject));
            searchCustomer.sendKeys("Anh Tester");
            Thread.sleep(2000); // chờ ajax trả kết quả
            searchCustomer.sendKeys(Keys.ENTER);

            // Billing type
            driver.findElement(By.xpath(LocatorsCRM.dropdownBillingType)).click();
            Thread.sleep(500);
            driver.findElement(By.xpath(LocatorsCRM.optionOfDropdown(LocatorsCRM.dropdownBillingType, "Fixed Rate"))).click();

            // Status
            driver.findElement(By.xpath(LocatorsCRM.dropdownStatus)).click();
            Thread.sleep(500);
            driver.findElement(By.xpath(LocatorsCRM.optionOfDropdown(LocatorsCRM.dropdownStatus, "In Progress"))).click();

            driver.findElement(By.xpath("//input[@id='project_cost']")).sendKeys("1000");
            driver.findElement(By.xpath(LocatorsCRM.inputEstimatedHours)).sendKeys("100");

            WebElement startDate = driver.findElement(By.xpath(LocatorsCRM.inputStartDate));
            startDate.clear();
            startDate.sendKeys("01-01-2026");
            WebElement deadline = driver.findElement(By.xpath(LocatorsCRM.inputDeadline));
            deadline.clear();
            deadline.sendKeys("31-12-2026");
            deadline.sendKeys(Keys.ESCAPE);

            // Description nằm trong iframe TinyMCE
            driver.switchTo().frame(driver.findElement(By.xpath(LocatorsCRM.iframeDescription)));
            driver.findElement(By.xpath(LocatorsCRM.bodyDescription)).sendKeys("Dự án demo tạo bằng Selenium WebDriver");
            driver.switchTo().defaultContent();

            driver.findElement(By.xpath(LocatorsCRM.buttonSaveProject)).click();
            Thread.sleep(2000);
            System.out.println("URL sau khi Save: " + driver.getCurrentUrl());

            // Kiểm tra lại ở trang danh sách
            driver.navigate().to("https://crm.anhtester.com/admin/projects");
            driver.findElement(By.xpath(LocatorsCRM.inputSearchProject)).sendKeys(projectName);
            Thread.sleep(2000);
            String actualName = driver.findElement(By.xpath(LocatorsCRM.firstRowProjectName)).getText();
            System.out.println("Project vừa thêm: " + actualName);
            System.out.println(actualName.equals(projectName) ? "PASSED: Add New Project" : "FAILED: Add New Project");

            driver.navigate().back();
            driver.navigate().refresh();
        } finally {
            driver.quit();
        }
    }
}
