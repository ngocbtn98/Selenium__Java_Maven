package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.helpers.ExcelHelper;
import com.anhtester.helpers.PropertiesHelper;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Login")
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Login thành công với tài khoản trong config.properties")
    public void testLoginSuccess() {
        new LoginPage().loginCMS();
    }

    /** BT10: đọc data login từ Excel, ghi kết quả vào cột RESULT */
    @Test(priority = 2, dataProvider = "data_login", dataProviderClass = DataProviderFactory.class,
            description = "Login với data từ Excel")
    public void testLoginWithExcelData(Hashtable<String, String> data) {
        LoginPage loginPage = new LoginPage();
        loginPage.tryLogin(data.get("EMAIL"), data.get("PASSWORD"));
        if ("SUCCESS".equalsIgnoreCase(data.get("EXPECTED"))) {
            loginPage.verifyLoginSuccess();
        } else {
            loginPage.verifyLoginFail();
        }
        writeResult(data, "PASSED");
    }

    private static synchronized void writeResult(Hashtable<String, String> data, String result) {
        ExcelHelper excel = new ExcelHelper();
        excel.setExcelFile(PropertiesHelper.getValue("excel.data.path"), "Login");
        for (int i = 1; i <= excel.getRowCount(); i++) {
            if (excel.getCellData("EMAIL", i).equals(data.get("EMAIL"))
                    && excel.getCellData("PASSWORD", i).equals(data.get("PASSWORD"))) {
                excel.setCellData(result, "RESULT", i);
                return;
            }
        }
    }
}
