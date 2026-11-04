package com.anhtester.pages;

import com.anhtester.helpers.CaptureHelper;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.keywords.WebUI;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * BÀI TẬP 5: Page Login => Dashboard.
 */
public class LoginPage {

    private final By inputEmail = By.xpath("//input[@id='email']");
    private final By inputPassword = By.xpath("//input[@id='password']");
    private final By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private final By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");

    @Step("Open Login page")
    public void openLoginPage() {
        WebUI.openURL(PropertiesHelper.getValue("url.login"));
        CaptureHelper.takeScreenshotPage("Login");
    }

    @Step("Enter email {0}")
    public void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    @Step("Click Login")
    public void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
        WebUI.waitForPageLoaded();
    }

    /** Login với tài khoản trong config.properties (BT11) */
    public DashboardPage loginCMS() {
        return loginCMS(PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password"));
    }

    @Step("Login CMS with {0}")
    public DashboardPage loginCMS(String email, String password) {
        openLoginPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        verifyLoginSuccess();
        return new DashboardPage();
    }

    /** Dùng cho data test login sai (không verify thành công) */
    @Step("Try login with {0}")
    public void tryLogin(String email, String password) {
        openLoginPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    @Step("Verify login success")
    public void verifyLoginSuccess() {
        WebUI.waitForElementVisible(menuDashboard);
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/admin", "Login không thành công");
        CaptureHelper.takeScreenshotPage("Dashboard");
    }

    @Step("Verify login fail")
    public void verifyLoginFail() {
        WebUI.waitForElementVisible(buttonLogin);
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/login", "Vẫn login được với tài khoản sai");
        WebUI.verifyTrue(!WebUI.checkElementExist(menuDashboard), "Không được vào Dashboard khi login sai");
        CaptureHelper.takeScreenshotPage("LoginFail");
    }
}
