package javaoop.bt3.common;

/**
 * BT-JAVA OOP 3: Class cha chứa createDriver và closeDriver.
 * Các biến được đóng gói (private) và truy cập qua get/set.
 */
public class BaseTest {
    private String browser;
    private boolean report;
    private boolean headless;

    public BaseTest() {
        this.browser = Constants.browser;
        this.report = Constants.report;
        this.headless = Constants.headless;
    }

    public void createDriver() {
        System.out.println("===== Create Driver =====");
        System.out.println("Browser: " + browser);
        System.out.println("Report: " + report);
        System.out.println("Headless: " + headless);
    }

    public void closeDriver(String browser) {
        System.out.println("Closed browser: " + browser);
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public boolean isHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }
}
