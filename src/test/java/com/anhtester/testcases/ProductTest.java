package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.helpers.SystemHelper;
import com.anhtester.pages.CategoryPage;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import com.anhtester.pages.ProductPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Product")
public class ProductTest extends BaseTest {
    private DashboardPage dashboardPage;
    private ProductPage productPage;

    @BeforeMethod(alwaysRun = true) // TestNG chạy @BeforeMethod của BaseTest (createDriver) trước
    public void login() {
        dashboardPage = new LoginPage().loginCMS();
    }

    private String addProduct(Hashtable<String, String> data, String category) {
        String name = data.get("PRODUCT_NAME") + " " + SystemHelper.getDateTimeNow();
        productPage = dashboardPage.openAddProductPage();
        productPage.addProduct(name, category, data.get("BRAND"), data.get("UNIT"), data.get("WEIGHT"), data.get("MIN_QTY"),
                data.get("TAG"), data.get("COLORS"), data.get("UNIT_PRICE"), data.get("DISCOUNT"), data.get("QUANTITY"), "");
        return name;
    }

    @Test(priority = 1, description = "Add product với category có sẵn", dataProvider = "data_product", dataProviderClass = DataProviderFactory.class)
    public void testAddProduct(Hashtable<String, String> data) {
        String name = addProduct(data, data.get("CATEGORY"));
        dashboardPage.openAllProductsPage().verifyProductInTable(name);
    }

    @Test(priority = 2, description = "Edit product", dataProvider = "data_product", dataProviderClass = DataProviderFactory.class)
    public void testEditProduct(Hashtable<String, String> data) {
        String name = addProduct(data, data.get("CATEGORY"));
        String newName = name + " Edited";
        ProductPage allProducts = dashboardPage.openAllProductsPage();
        allProducts.verifyAllProductsPage();
        allProducts.editProduct(name, newName, data.get("NEW_UNIT_PRICE"));
        dashboardPage.openAllProductsPage().verifyProductInTable(newName);
    }

    @Test(priority = 3, description = "Luồng Add Category -> Add Product dùng category vừa tạo",
            dataProvider = "data_product", dataProviderClass = DataProviderFactory.class)
    public void testAddCategoryThenProduct(Hashtable<String, String> data) {
        String categoryName = "Category For Product " + SystemHelper.getDateTimeNow();
        CategoryPage categoryPage = dashboardPage.openCategoryPage();
        categoryPage.addCategory(categoryName, "", "1", "Physical", categoryName, categoryName);

        String name = addProduct(data, categoryName);
        dashboardPage.openAllProductsPage().verifyProductInTable(name);
    }

    @Test(priority = 4, description = "BT9: Search data table Product")
    public void testSearchProductTable() {
        productPage = dashboardPage.openAllProductsPage();
        productPage.searchDataTable("Product", 2);
    }
}
