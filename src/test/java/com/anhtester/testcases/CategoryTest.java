package com.anhtester.testcases;

import com.anhtester.common.BaseTest;
import com.anhtester.dataproviders.DataProviderFactory;
import com.anhtester.helpers.SystemHelper;
import com.anhtester.pages.CategoryPage;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("eCommerce CMS")
@Feature("Category")
public class CategoryTest extends BaseTest {
    private DashboardPage dashboardPage;
    private CategoryPage categoryPage;

    @BeforeMethod(alwaysRun = true) // TestNG chạy @BeforeMethod của BaseTest (createDriver) trước
    public void login() {
        dashboardPage = new LoginPage().loginCMS();
        dashboardPage.verifyDashboardPage();
    }

    private String addCategory(Hashtable<String, String> data) {
        String name = data.get("CATEGORY_NAME") + " " + SystemHelper.getDateTimeNow();
        categoryPage = dashboardPage.openCategoryPage();
        categoryPage.verifyCategoryPage();
        categoryPage.addCategory(name, data.get("PARENT_CATEGORY"), data.get("ORDERING_NUMBER"), data.get("TYPE"),
                data.get("META_TITLE"), data.get("META_DESCRIPTION"));
        return name;
    }

    @Test(priority = 1, description = "Add new category", dataProvider = "data_category", dataProviderClass = DataProviderFactory.class)
    public void testAddCategory(Hashtable<String, String> data) {
        String name = addCategory(data);
        dashboardPage.openCategoryPage();
        categoryPage.verifyCategoryInTable(name);
    }

    @Test(priority = 2, description = "Edit category", dataProvider = "data_category", dataProviderClass = DataProviderFactory.class)
    public void testEditCategory(Hashtable<String, String> data) {
        String name = addCategory(data);
        String newName = name + " Edited";
        dashboardPage.openCategoryPage();
        categoryPage.editCategory(name, newName);
        dashboardPage.openCategoryPage();
        categoryPage.verifyCategoryInTable(newName);
    }

    @Test(priority = 3, description = "Delete category", dataProvider = "data_category", dataProviderClass = DataProviderFactory.class)
    public void testDeleteCategory(Hashtable<String, String> data) {
        String name = addCategory(data);
        dashboardPage.openCategoryPage();
        categoryPage.deleteCategory(name);
        dashboardPage.openCategoryPage();
        categoryPage.verifyCategoryDeleted(name);
    }

    @Test(priority = 4, description = "BT9: Search data table Category")
    public void testSearchCategoryTable() {
        categoryPage = dashboardPage.openCategoryPage();
        categoryPage.searchDataTable("Category", 2);
    }
}
