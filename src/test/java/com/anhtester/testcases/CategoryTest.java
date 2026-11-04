package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.pages.CategoryPage;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

/**
 * BÀI TẬP 5: Test case Category theo POM (Login => Dashboard => Category).
 */
@Epic("eCommerce CMS")
@Feature("Category")
public class CategoryTest extends BaseTest {

    private CategoryPage loginAndOpenCategory() {
        DashboardPage dashboardPage = new LoginPage().loginCMS();
        return dashboardPage.openCategoryPage();
    }

    @Test(priority = 1, dataProvider = "data_category", dataProviderClass = DataProviderFactory.class,
            description = "Add Category")
    public void testAddCategory(Hashtable<String, String> data) {
        CategoryPage categoryPage = loginAndOpenCategory();
        String name = categoryPage.addCategory(data);
        categoryPage.verifyCategoryInTable(name);
    }

    @Test(priority = 2, dataProvider = "data_category", dataProviderClass = DataProviderFactory.class,
            description = "Edit Category")
    public void testEditCategory(Hashtable<String, String> data) {
        CategoryPage categoryPage = loginAndOpenCategory();
        String oldName = categoryPage.addCategory(data);
        String newName = data.get("NEW_NAME") + " " + System.currentTimeMillis();
        categoryPage.editCategory(oldName, newName);
        categoryPage.verifyCategoryInTable(newName);
    }

    @Test(priority = 3, dataProvider = "data_category", dataProviderClass = DataProviderFactory.class,
            description = "Delete Category")
    public void testDeleteCategory(Hashtable<String, String> data) {
        CategoryPage categoryPage = loginAndOpenCategory();
        String name = categoryPage.addCategory(data);
        categoryPage.deleteCategory(name);
        categoryPage.verifyCategoryNotExist(name);
    }

    /** BÀI TẬP 9: search Web Table */
    @Test(priority = 4, dataProvider = "data_category", dataProviderClass = DataProviderFactory.class,
            description = "Search Category trong Web Table")
    public void testSearchCategoryTable(Hashtable<String, String> data) {
        CategoryPage categoryPage = loginAndOpenCategory();
        categoryPage.searchAndCheckTable(data.get("NAME"));
    }
}
