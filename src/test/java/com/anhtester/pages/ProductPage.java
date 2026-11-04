package com.anhtester.pages;

import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class ProductPage extends BasePage {
    private final By headerAddNewProduct = By.xpath("//h5[normalize-space()='Add New Product']");
    private final By headerEditProduct = By.xpath("//h5[normalize-space()='Edit Product'] | //h1[normalize-space()='Edit Product']");
    private final By headerAllProducts = By.xpath("//h1[normalize-space()='All Products']");
    private final By inputProductName = By.xpath("//input[@name='name']");
    private final By inputUnit = By.xpath("//input[@name='unit']");
    private final By inputWeight = By.xpath("//input[@name='weight']");
    private final By inputMinQty = By.xpath("//input[@name='min_qty']");
    private final By inputTags = By.xpath("//span[contains(@class,'tagify__input')]");
    private final By toggleColors = By.xpath("//input[@name='colors_active']/following-sibling::span");
    private final By checkboxColors = By.xpath("//input[@name='colors_active']");
    private final By dropdownColors = By.xpath("//button[@data-id='colors']");
    private final By inputSearchColors = By.xpath("//button[@data-id='colors']/following-sibling::div//input[@type='search' or @type='text']");
    private final By selectColors = By.xpath("//select[@id='colors']");
    private final By inputUnitPrice = By.xpath("//input[@name='unit_price']");
    private final By inputDiscount = By.xpath("//input[@name='discount']");
    private final By inputQuantity = By.xpath("//input[@name='current_stock']");
    private final By inputSku = By.xpath("//input[@name='sku']");
    private final By buttonSavePublish = By.xpath("//button[normalize-space()='Save & Publish']");
    private final By buttonUpdateProduct = By.xpath("//button[normalize-space()='Update Product']");

    public void verifyAddProductPage() {
        WebUI.verifyEquals(WebUI.getElementText(headerAddNewProduct), "Add New Product", "Header trang Add Product không đúng");
        WebUI.takeScreenshotPage("AddProductPage");
    }

    public void verifyAllProductsPage() {
        WebUI.verifyElementVisible(headerAllProducts, "Trang All Products không hiển thị");
        WebUI.takeScreenshotPage("AllProductsPage");
    }

    public void enterProductName(String name) {
        WebUI.setText(inputProductName, name);
    }

    public void selectCategory(String category) {
        WebUI.selectBootstrapDropdown("category_id", category);
    }

    public void selectBrand(String brand) {
        if (brand != null && !brand.isEmpty()) {
            WebUI.selectBootstrapDropdown("brand_id", brand);
        }
    }

    public void enterUnit(String unit) {
        WebUI.setText(inputUnit, unit);
    }

    public void enterWeight(String weight) {
        if (weight != null && !weight.isEmpty()) WebUI.setText(inputWeight, weight);
    }

    public void enterMinQty(String qty) {
        WebUI.clearTextWithKeys(inputMinQty);
        WebUI.setText(inputMinQty, qty);
    }

    public void enterTag(String tag) {
        if (tag != null && !tag.isEmpty()) {
            WebUI.clickElement(inputTags);
            WebUI.getWebElement(inputTags).sendKeys(tag, Keys.ENTER);
        }
    }

    /** Chọn nhiều màu, dùng Ctrl + A và Delete để xoá ô search trước mỗi màu */
    public void selectColors(String... colors) {
        if (!WebUI.getWebElement(checkboxColors).isSelected()) {
            WebUI.clickElement(toggleColors);
        }
        WebUI.clickElement(dropdownColors);
        for (String color : colors) {
            WebUI.clearTextWithKeys(inputSearchColors);
            WebUI.setTextAndKey(inputSearchColors, color.trim(), Keys.ENTER);
        }
        WebUI.clearTextWithKeys(inputSearchColors);
        WebUI.pressKey(Keys.ESCAPE);
    }

    public void verifyNumberOfColors(int expected) {
        WebUI.verifyEquals(WebUI.getAllSelectedOptions(selectColors).size(), expected, "Số lượng color được chọn không đúng");
    }

    public void enterPrice(String unitPrice, String discount, String quantity, String sku) {
        WebUI.scrollToElement(inputUnitPrice);
        WebUI.setText(inputUnitPrice, unitPrice);
        WebUI.setText(inputDiscount, discount);
        if (WebUI.checkElementExist(inputQuantity) && WebUI.isElementDisplayed(inputQuantity)) {
            WebUI.setText(inputQuantity, quantity);
        }
        if (sku != null && !sku.isEmpty() && WebUI.isElementDisplayed(inputSku)) {
            WebUI.setText(inputSku, sku);
        }
    }

    public void addProduct(String name, String category, String brand, String unit, String weight, String minQty, String tag,
                           String colors, String unitPrice, String discount, String quantity, String sku) {
        verifyAddProductPage();
        enterProductName(name);
        selectCategory(category);
        selectBrand(brand);
        enterUnit(unit);
        enterWeight(weight);
        enterMinQty(minQty);
        enterTag(tag);
        if (colors != null && !colors.isEmpty()) {
            String[] colorList = colors.split(",");
            selectColors(colorList);
            verifyNumberOfColors(colorList.length);
        }
        enterPrice(unitPrice, discount, quantity, sku);
        WebUI.clickElement(buttonSavePublish);
        verifyToastMessage("Product has been inserted successfully");
    }

    public void searchProduct(String name) {
        search(name);
    }

    public void verifyProductInTable(String name) {
        searchProduct(name);
        WebUI.verifyContains(WebUI.getElementText(firstRowName), name, "Product không có trong table");
    }

    public void editProduct(String oldName, String newName, String newUnitPrice) {
        searchProduct(oldName);
        WebUI.clickElement(firstRowEdit);
        WebUI.verifyElementVisible(headerEditProduct, "Trang Edit Product không hiển thị");
        WebUI.verifyEquals(WebUI.getElementAttribute(inputProductName, "value"), oldName, "Tên product ở form edit không đúng");
        enterProductName(newName);
        if (newUnitPrice != null && !newUnitPrice.isEmpty()) {
            WebUI.scrollToElement(inputUnitPrice);
            WebUI.setText(inputUnitPrice, newUnitPrice);
        }
        WebUI.takeScreenshotPage("EditProductPage");
        WebUI.clickElement(buttonUpdateProduct);
        verifyToastMessage("Product has been updated successfully");
    }
}
