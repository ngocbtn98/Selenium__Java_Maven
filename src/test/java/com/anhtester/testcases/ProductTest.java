package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.pages.CategoryPage;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import com.anhtester.pages.ProductPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

/**
 * BÀI TẬP 6: Test case Product theo POM (add, edit, search) + luồng Category -> Product.
 */
@Epic("eCommerce CMS")
@Feature("Product")
public class ProductTest extends BaseTest {

    @Test(priority = 1, dataProvider = "data_product", dataProviderClass = DataProviderFactory.class,
            description = "Add Product với Category có sẵn")
    public void testAddProduct(Hashtable<String, String> data) {
        DashboardPage dashboardPage = new LoginPage().loginCMS();
        ProductPage productPage = dashboardPage.openAddNewProductPage();
        String name = productPage.addProduct(data, data.get("CATEGORY"));
        productPage.verifyProductInTable(name);
    }

    @Test(priority = 2, dataProvider = "data_product", dataProviderClass = DataProviderFactory.class,
            description = "Edit Product")
    public void testEditProduct(Hashtable<String, String> data) {
        DashboardPage dashboardPage = new LoginPage().loginCMS();
        ProductPage productPage = dashboardPage.openAddNewProductPage();
        String oldName = productPage.addProduct(data, data.get("CATEGORY"));
        String newName = data.get("NEW_NAME") + " " + System.currentTimeMillis();
        productPage.editProduct(oldName, newName, data.get("NEW_UNIT_PRICE"));
        productPage.verifyProductInTable(newName);
    }

    /** BT4-Part 2: Add Category rồi dùng lại category đó để Add Product */
    @Test(priority = 3, dataProvider = "data_category_product", dataProviderClass = DataProviderFactory.class,
            description = "Luồng Add Category -> Add Product")
    public void testAddCategoryThenProduct(Hashtable<String, String> category, Hashtable<String, String> product) {
        DashboardPage dashboardPage = new LoginPage().loginCMS();
        CategoryPage categoryPage = dashboardPage.openCategoryPage();
        String categoryName = categoryPage.addCategory(category);

        ProductPage productPage = categoryPage.openAddNewProductPage();
        String productName = productPage.addProduct(product, categoryName);
        productPage.verifyProductInTable(productName);
    }

    /** BÀI TẬP 9: search Web Table */
    @Test(priority = 4, dataProvider = "data_product", dataProviderClass = DataProviderFactory.class,
            description = "Search Product trong Web Table")
    public void testSearchProductTable(Hashtable<String, String> data) {
        DashboardPage dashboardPage = new LoginPage().loginCMS();
        ProductPage productPage = dashboardPage.openProductPage();
        productPage.searchAndCheckTable(data.get("NAME"));
    }
}
