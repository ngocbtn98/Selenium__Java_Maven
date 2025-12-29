package test123;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class demo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("//htpt");
        Thread.sleep(2000);//dung 2s
        driver.findElement(By.id("email")).sendKeys("abc@mail");
        driver.findElement(By.name("pass")).sendKeys("123");
        driver.findElement(By.className("gjhgad")).click();
        Thread.sleep(2000);
        driver.quit();

    }
}
