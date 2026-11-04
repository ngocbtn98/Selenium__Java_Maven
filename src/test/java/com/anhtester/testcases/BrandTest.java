package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.helpers.SystemHelper;
import com.anhtester.pages.BrandPage;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Brand")
public class BrandTest extends BaseTest {

    @Test(description = "Add new brand", dataProvider = "data_brand", dataProviderClass = DataProviderFactory.class)
    public void testAddBrand(Hashtable<String, String> data) {
        String name = data.get("BRAND_NAME") + " " + SystemHelper.getDateTimeNow();
        BrandPage brandPage = new LoginPage().loginCMS().openBrandPage();
        brandPage.verifyBrandPage();
        brandPage.addBrand(name, data.get("META_TITLE"), data.get("META_DESCRIPTION"));
        brandPage.verifyBrandInTable(name);
    }
}
