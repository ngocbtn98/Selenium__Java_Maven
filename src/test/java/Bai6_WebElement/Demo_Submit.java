package Bai6_WebElement;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Demo_Submit {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crm.anhtester.com/admin/authentication");

        boolean checkLoginPage = driver.findElement(By.xpath(LocatorsCRM.headerLoginPage)).isDisplayed();
        System.out.println("Check Login Page: " + checkLoginPage);

        driver.findElement(By.xpath(LocatorsCRM.inputEmail)).clear();
        driver.findElement(By.xpath(LocatorsCRM.inputEmail)).sendKeys("admin@example.com");


        driver.findElement(By.xpath(LocatorsCRM.inputPassword)).clear();
        driver.findElement(By.xpath(LocatorsCRM.inputPassword)).sendKeys("123456");

        driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).submit(); // Sử dụng phương thức submit để gửi form
        Thread.sleep(2000); // Dừng 2 giây để quan sát kết quả

        //driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).click();

        driver.quit();
    }
}
