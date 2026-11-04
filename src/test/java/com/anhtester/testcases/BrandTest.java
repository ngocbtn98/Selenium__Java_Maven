package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.pages.BrandPage;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Brand")
public class BrandTest extends BaseTest {

    @Test(dataProvider = "data_brand", dataProviderClass = DataProviderFactory.class, description = "Add Brand")
    public void testAddBrand(Hashtable<String, String> data) {
        BrandPage brandPage = new LoginPage().loginCMS().openBrandPage();
        String name = brandPage.addBrand(data);
        brandPage.verifyBrandInTable(name);
    }
}
