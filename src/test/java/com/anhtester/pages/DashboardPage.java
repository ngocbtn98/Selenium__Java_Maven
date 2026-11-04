package com.anhtester.pages;

import com.anhtester.keywords.WebUI;

public class DashboardPage extends BasePage {

    public void verifyDashboardPage() {
        WebUI.verifyElementVisible(menuDashboard, "Trang Dashboard không hiển thị");
        WebUI.takeScreenshotPage("DashboardPage");
    }

    public CategoryPage openCategoryPage() {
        expandProductsMenu(menuCategory);
        return new CategoryPage();
    }

    public ProductPage openAllProductsPage() {
        expandProductsMenu(menuAllProducts);
        return new ProductPage();
    }

    public ProductPage openAddProductPage() {
        expandProductsMenu(menuAddNewProduct);
        return new ProductPage();
    }

    public BrandPage openBrandPage() {
        expandProductsMenu(menuBrand);
        return new BrandPage();
    }
}
