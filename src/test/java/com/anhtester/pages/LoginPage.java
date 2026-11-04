package com.anhtester.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By buttonAcceptCookie = By.xpath("//button[normalize-space()='Ok. I Understood']");
    private final By headerLogin = By.xpath("//h1[normalize-space()='Login to your account.'] | //h1[contains(normalize-space(),'Login')]");
    private final By inputEmail = By.xpath("//input[@id='email']");
    private final By inputPassword = By.xpath("//input[@id='password']");
    private final By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private final By errorEmailInvalid = By.xpath("//input[@id='email']/following-sibling::span[contains(@class,'invalid-feedback')]");

    public void openLoginPage() {
        WebUI.openURL(ConfigData.URL());
        if (WebUI.isElementDisplayed(buttonAcceptCookie)) {
            WebUI.clickElement(buttonAcceptCookie);
        }
        WebUI.takeScreenshotPage("LoginPage");
    }

    public void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    public void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    public void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
    }

    /** Login => trả về DashboardPage (liên kết trang) */
    public DashboardPage loginCMS(String email, String password) {
        openLoginPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        WebUI.waitForPageLoaded();
        return new DashboardPage();
    }

    public DashboardPage loginCMS() {
        return loginCMS(ConfigData.EMAIL(), ConfigData.PASSWORD());
    }

    public void verifyLoginSuccess() {
        WebUI.verifyElementVisible(menuDashboard, "Login không thành công - Dashboard không hiển thị");
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/admin", "URL sau khi login không phải trang admin");
    }

    public void verifyLoginFail(String expectedMessage) {
        WebUI.verifyTrue(WebUI.isElementDisplayed(buttonLogin), "Login fail nhưng không còn ở trang Login");
        if (expectedMessage != null && !expectedMessage.isEmpty()) {
            if (WebUI.checkElementExist(messageToast)) {
                WebUI.verifyContains(getToastMessage(), expectedMessage, "Message login fail không đúng");
            } else if (WebUI.checkElementExist(errorEmailInvalid)) {
                WebUI.verifyContains(WebUI.getElementText(errorEmailInvalid), expectedMessage, "Message login fail không đúng");
            }
        }
    }
}
