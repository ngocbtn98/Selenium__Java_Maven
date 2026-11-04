package javaoop.bai3_inheritance.common;

public class BaseTest {
    private String browser;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void createDriver() {
        setBrowser(Constants.browser);
        System.out.println("Browser: " + Constants.browser);
        System.out.println("Report: " + Constants.report);
        System.out.println("Headless: " + Constants.headless);
    }

    public void closeDriver(String browser) {
        System.out.println("Closed browser: " + browser);
    }
}
