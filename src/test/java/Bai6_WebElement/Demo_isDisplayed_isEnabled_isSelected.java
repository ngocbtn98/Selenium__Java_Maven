package Bai6_WebElement;

import Bai5_locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Demo_isDisplayed_isEnabled_isSelected {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crm.anhtester.com/admin/authentication");

        //Handle isDisplayed
        boolean checkHeaderLoginPageDisplayed = driver.findElement(By.xpath(LocatorsCRM.headerLoginPage)).isDisplayed();
        System.out.println("Check Header Login Page is Displayed: " + checkHeaderLoginPageDisplayed);
        Thread.sleep(2000); // Dừng 2 giây để quan sát

        //Handle isSelected
        boolean checkCheckboxRememberMeSelected = driver.findElement(By.xpath(LocatorsCRM.checkboxRememberMe)).isSelected();
        System.out.println("Check Checkbox Remember Me is Selected: " + checkCheckboxRememberMeSelected);
        driver.findElement(By.xpath(LocatorsCRM.labelRememberMe)).click();
        Thread.sleep(2000); // Dừng 2 giây để quan sát

        boolean checkCheckboxRememberMeSelected_AfterClick = driver.findElement(By.xpath(LocatorsCRM.checkboxRememberMe)).isSelected();
        System.out.println("Check Checkbox Remember Me is Selected After Click: " + checkCheckboxRememberMeSelected_AfterClick);
        Thread.sleep(2000); // Dừng 2 giây để quan sát

        //Handle isEnabled
        boolean checkButtonLoginEnabled = driver.findElement(By.xpath(LocatorsCRM.buttonLogin)).isEnabled();
        System.out.println("Check Button Login is Enabled: " + checkButtonLoginEnabled);
        Thread.sleep(2000); // Dừng 2 giây để quan sát

        driver.quit();

    }
}
