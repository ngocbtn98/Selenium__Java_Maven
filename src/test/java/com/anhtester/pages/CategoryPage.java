package com.anhtester.pages;

import com.anhtester.helpers.CaptureHelper;
import com.anhtester.keywords.WebUI;
import com.anhtester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.Hashtable;

/**
 * BÀI TẬP 5: Page Category (add / edit / delete / search).
 * BÀI TẬP 9: hàm search data table.
 */
public class CategoryPage extends BasePage {

    // List
    private final By headerAllCategories = By.xpath("//h1[normalize-space()='All categories' or normalize-space()='All Categories']");
    private final By buttonAddNewCategory = By.xpath("//span[normalize-space()='Add New category' or normalize-space()='Add New Category']");
    private final By inputSearch = By.xpath("//input[@id='search']");
    private final By tableRows = By.xpath("//table//tbody/tr");
    private final By firstRowName = By.xpath("//table//tbody/tr[1]/td[2]");
    private final By firstRowEdit = By.xpath("//table//tbody/tr[1]//a[@title='Edit']");
    private final By firstRowDelete = By.xpath("//table//tbody/tr[1]//a[@title='Delete']");
    private final By headerDeleteConfirm = By.xpath("//h4[normalize-space()='Delete Confirmation']");
    private final By buttonConfirmDelete = By.xpath("//a[@id='delete-link']");
    private final By nothingFound = By.xpath("//td[normalize-space()='Nothing found']");

    // Form Add / Edit
    private final By headerCategoryInformation = By.xpath("//h5[normalize-space()='Category Information']");
    private final By inputName = By.xpath("//input[@id='name']");
    private final By inputOrderingNumber = By.xpath("//input[@id='order_level']");
    private final By buttonBrowseBanner = By.xpath("//label[contains(normalize-space(),'Banner')]/following-sibling::div//div[normalize-space()='Browse']");
    private final By buttonBrowseIcon = By.xpath("//label[contains(normalize-space(),'Icon')]/following-sibling::div//div[normalize-space()='Browse']");
    private final By inputMetaTitle = By.xpath("//input[@name='meta_title']");
    private final By textareaMetaDescription = By.xpath("//textarea[@name='meta_description']");
    private final By buttonSave = By.xpath("//button[normalize-space()='Save']");

    @Step("Verify Category page")
    public void verifyCategoryPage() {
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/admin/categories", "Không vào được trang Category");
        WebUI.verifyEquals(WebUI.getElementText(headerAllCategories).toLowerCase(), "all categories", "Header trang Category sai");
    }

    @Step("Click Add New Category")
    public void clickAddNewCategory() {
        WebUI.clickElement(buttonAddNewCategory);
        WebUI.verifyEquals(WebUI.getElementText(headerCategoryInformation), "Category Information", "Header trang Add Category sai");
        CaptureHelper.takeScreenshotPage("AddCategory");
    }

    /**
     * Add category với data từ Excel. Tên được nối thêm timestamp để không trùng khi chạy lại.
     * @return tên category vừa add
     */
    @Step("Add new Category")
    public String addCategory(Hashtable<String, String> data) {
        String name = data.get("NAME") + " " + System.currentTimeMillis();
        LogUtils.info("Add category: " + name);

        verifyCategoryPage();
        clickAddNewCategory();
        WebUI.setText(inputName, name);
        WebUI.setText(inputOrderingNumber, data.get("ORDER"));
        chooseImage(buttonBrowseBanner, data.get("IMAGE"));
        chooseImage(buttonBrowseIcon, data.get("IMAGE"));
        WebUI.setText(inputMetaTitle, data.get("META_TITLE"));
        WebUI.setText(textareaMetaDescription, data.get("META_DESCRIPTION"));
        WebUI.clickElement(buttonSave);
        verifyMessage("Category has been inserted successfully");
        return name;
    }

    @Step("Search Category {0}")
    public void searchCategory(String name) {
        openCategoryPage();
        WebUI.setTextAndKey(inputSearch, name, Keys.ENTER);
        WebUI.waitForPageLoaded();
    }

    @Step("Verify Category {0} in table")
    public void verifyCategoryInTable(String name) {
        searchCategory(name);
        WebUI.waitForTextInElement(firstRowName, name);
        WebUI.verifyEquals(WebUI.getElementText(firstRowName), name, "Tên category trong bảng không đúng");
    }

    /** BÀI TẬP 9: search rồi kiểm tra tất cả dòng cột Name đều chứa từ khoá */
    @Step("Search Category table with keyword {0}")
    public void searchAndCheckTable(String keyword) {
        searchCategory(keyword);
        WebUI.waitForTextInElement(firstRowName, keyword);
        WebUI.checkSearchTableByColumn(tableRows, 2, keyword);
    }

    @Step("Edit Category {0} -> {1}")
    public void editCategory(String oldName, String newName) {
        searchCategory(oldName);
        WebUI.waitForTextInElement(firstRowName, oldName);
        WebUI.clickElement(firstRowEdit);
        WebUI.verifyEquals(WebUI.getElementText(headerCategoryInformation), "Category Information", "Header trang Edit sai");
        WebUI.verifyEquals(WebUI.getAttributeElement(inputName, "value"), oldName, "Giá trị Name cũ ở trang Edit sai");
        CaptureHelper.takeScreenshotPage("EditCategory");

        WebUI.setText(inputName, newName);
        WebUI.setText(inputMetaTitle, newName);
        WebUI.clickElement(buttonSave);
        verifyMessage("Category has been updated successfully");
    }

    @Step("Delete Category {0}")
    public void deleteCategory(String name) {
        searchCategory(name);
        WebUI.waitForTextInElement(firstRowName, name);
        WebUI.clickElement(firstRowDelete);
        WebUI.verifyEquals(WebUI.getElementText(headerDeleteConfirm), "Delete Confirmation", "Header dialog Delete sai");
        WebUI.clickElement(buttonConfirmDelete);
        verifyMessage("Category has been deleted successfully");
    }

    @Step("Verify Category {0} not exist")
    public void verifyCategoryNotExist(String name) {
        searchCategory(name);
        WebUI.waitForElementVisible(nothingFound);
        WebUI.verifyTrue(!WebUI.checkElementExist(By.xpath("//table//tbody//td[normalize-space()='" + name + "']")),
                "Category vẫn còn tồn tại sau khi xoá");
    }
}
