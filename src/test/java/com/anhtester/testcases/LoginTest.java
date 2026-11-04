package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Login thành công với tài khoản admin",
            dataProvider = "data_login_success", dataProviderClass = DataProviderFactory.class)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login thành công => hiển thị trang Dashboard")
    public void testLoginSuccess(Hashtable<String, String> data) {
        LoginPage loginPage = new LoginPage();
        loginPage.loginCMS(data.get("EMAIL"), data.get("PASSWORD"));
        loginPage.verifyLoginSuccess();
    }

    @Test(priority = 2, description = "Login thất bại với email/password sai",
            dataProvider = "data_login_fail", dataProviderClass = DataProviderFactory.class)
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginFail(Hashtable<String, String> data) {
        LoginPage loginPage = new LoginPage();
        loginPage.loginCMS(data.get("EMAIL"), data.get("PASSWORD"));
        loginPage.verifyLoginFail(data.get("EXPECTED_MESSAGE"));
    }
}
