package com.anhtester.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;

public class CategoryPage extends BasePage {
    private final By headerAllCategories = By.xpath("//h1[normalize-space()='All categories']");
    private final By buttonAddNewCategory = By.xpath("//span[normalize-space()='Add New category']");
    private final By headerCategoryInformation = By.xpath("//h5[normalize-space()='Category Information']");
    private final By inputName = By.xpath("//input[@id='name']");
    private final By inputOrderLevel = By.xpath("//input[@id='order_level']");
    private final By buttonBrowseBanner = By.xpath("//label[contains(normalize-space(),'Banner')]/following-sibling::div//div[normalize-space()='Browse']");
    private final By buttonBrowseIcon = By.xpath("//label[contains(normalize-space(),'Icon')]/following-sibling::div//div[normalize-space()='Browse']");
    private final By inputMetaTitle = By.xpath("//input[@name='meta_title']");
    private final By textareaMetaDescription = By.xpath("//textarea[@name='meta_description']");
    private final By buttonSave = By.xpath("//button[normalize-space()='Save']");

    public void verifyCategoryPage() {
        WebUI.verifyEquals(WebUI.getElementText(headerAllCategories), "All categories", "Header trang Category không đúng");
        WebUI.takeScreenshotPage("CategoryPage");
    }

    public void clickAddNewCategory() {
        WebUI.clickElement(buttonAddNewCategory);
        WebUI.verifyEquals(WebUI.getElementText(headerCategoryInformation), "Category Information", "Header trang Add Category không đúng");
    }

    public void enterCategoryName(String name) {
        WebUI.setText(inputName, name);
    }

    public void enterOrderingNumber(String order) {
        WebUI.setText(inputOrderLevel, order);
    }

    public void selectParentCategory(String parent) {
        if (parent != null && !parent.isEmpty()) {
            WebUI.selectBootstrapDropdown("parent_id", parent);
        }
    }

    public void selectType(String type) {
        if (type != null && !type.isEmpty()) {
            WebUI.selectBootstrapDropdown("digital", type);
        }
    }

    public void selectBanner(String imageName) {
        WebUI.selectImageFromUploader(buttonBrowseBanner, imageName);
    }

    public void selectIcon(String imageName) {
        WebUI.selectImageFromUploader(buttonBrowseIcon, imageName);
    }

    public void enterMetaTitle(String title) {
        WebUI.setText(inputMetaTitle, title);
    }

    public void enterMetaDescription(String description) {
        WebUI.setText(textareaMetaDescription, description);
    }

    public void clickSave() {
        WebUI.clickElement(buttonSave);
    }

    public void addCategory(String name, String parent, String order, String type, String metaTitle, String metaDescription) {
        clickAddNewCategory();
        enterCategoryName(name);
        selectParentCategory(parent);
        enterOrderingNumber(order);
        selectType(type);
        selectBanner(ConfigData.IMAGE_NAME());
        selectIcon(ConfigData.IMAGE_NAME());
        enterMetaTitle(metaTitle);
        enterMetaDescription(metaDescription);
        WebUI.takeScreenshotPage("AddCategoryPage");
        clickSave();
        verifyToastMessage("Category has been inserted successfully");
    }

    public void searchCategory(String name) {
        search(name);
    }

    public void verifyCategoryInTable(String name) {
        searchCategory(name);
        WebUI.verifyEquals(WebUI.getElementText(firstRowName), name, "Category trong table không đúng");
    }

    public void editCategory(String oldName, String newName) {
        searchCategory(oldName);
        WebUI.clickElement(firstRowEdit);
        WebUI.verifyEquals(WebUI.getElementText(headerCategoryInformation), "Category Information", "Header trang Edit Category không đúng");
        WebUI.verifyEquals(WebUI.getElementAttribute(inputName, "value"), oldName, "Tên category ở form edit không đúng");
        enterCategoryName(newName);
        clickSave();
        verifyToastMessage("Category has been updated successfully");
    }

    public void deleteCategory(String name) {
        searchCategory(name);
        WebUI.clickElement(firstRowDelete);
        WebUI.verifyEquals(WebUI.getElementText(headerDeleteModal), "Delete Confirmation", "Header dialog delete không đúng");
        WebUI.verifyEquals(WebUI.getElementText(messageDeleteModal), "Are you sure to delete this?", "Message dialog delete không đúng");
        WebUI.clickElement(buttonConfirmDelete);
        verifyToastMessage("Category has been deleted successfully");
    }

    public void verifyCategoryDeleted(String name) {
        WebUI.setTextAndKey(inputSearch, name, org.openqa.selenium.Keys.ENTER);
        WebUI.waitForPageLoaded();
        WebUI.verifyTrue(!isItemInTable(name), "Category vẫn còn tồn tại sau khi xoá: " + name);
    }
}
