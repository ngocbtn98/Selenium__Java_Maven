package com.anhtester.pages;

import com.anhtester.drivers.DriverManager;
import com.anhtester.helpers.CaptureHelper;
import com.anhtester.keywords.WebUI;
import com.anhtester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Hashtable;

/**
 * BÀI TẬP 6: POM cho trang Product (add / edit / search).
 * BÀI TẬP 9: hàm search data table.
 */
public class ProductPage extends BasePage {

    // Form Add / Edit
    private final By inputName = By.xpath("//input[@name='name']");
    private final By dropdownCategory = By.xpath("//select[@name='category_id']/following-sibling::button");
    private final By inputSearchCategory = By.xpath("//select[@name='category_id']/following-sibling::div//input[@type='search']");
    private final By inputUnit = By.xpath("//input[@name='unit']");
    private final By inputMinQty = By.xpath("//input[@name='min_qty']");
    private final By inputTags = By.xpath("//tags[contains(@class,'tagify')]//span[contains(@class,'tagify__input')]");
    private final By switchColors = By.xpath("//input[@name='colors_active']/following-sibling::span");
    private final By dropdownColors = By.xpath("//select[@name='colors[]']/following-sibling::button");
    private final By inputSearchColors = By.xpath("//select[@name='colors[]']/following-sibling::div//input[@type='search']");
    private final By selectColors = By.xpath("//select[@name='colors[]']");
    private final By inputUnitPrice = By.xpath("//input[@name='unit_price']");
    private final By inputQuantity = By.xpath("//input[@name='current_stock']");
    private final By editorDescription = By.xpath("//div[contains(@class,'note-editable')]");
    private final By buttonSaveAndPublish = By.xpath("//button[normalize-space()='Save & Publish']");
    private final By buttonUpdateProduct = By.xpath("//button[normalize-space()='Update Product']");

    // List
    private final By inputSearch = By.xpath("//input[@id='search']");
    private final By tableRows = By.xpath("//table//tbody/tr");
    private final By firstRowName = By.xpath("//table//tbody/tr[1]/td[2]");
    private final By firstRowEdit = By.xpath("//table//tbody/tr[1]//a[@title='Edit']");

    @Step("Verify Add New Product page")
    public void verifyAddNewProductPage() {
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/admin/products/create", "Không vào được trang Add New Product");
    }

    /**
     * Add product với data Excel. categoryName truyền vào để dùng lại category vừa add (BT4-Part 2).
     * @return tên product vừa add
     */
    @Step("Add new Product with category {1}")
    public String addProduct(Hashtable<String, String> data, String categoryName) {
        String name = data.get("NAME") + " " + System.currentTimeMillis();
        LogUtils.info("Add product: " + name + " | category: " + categoryName);

        verifyAddNewProductPage();
        WebUI.setText(inputName, name);
        WebUI.selectSearchableDropdown(dropdownCategory, inputSearchCategory, categoryName);
        WebUI.verifyContains(WebUI.getElementText(dropdownCategory), categoryName, "Chọn Category sai");
        WebUI.setText(inputUnit, data.get("UNIT"));
        WebUI.setText(inputMinQty, data.get("MIN_QTY"));

        // Tags
        WebUI.clickElement(inputTags);
        WebUI.sendKeysWithActions(data.get("TAGS"));
        WebUI.pressKey(Keys.ENTER);

        selectColors(data.get("COLORS").split(","));

        WebUI.setText(inputUnitPrice, data.get("UNIT_PRICE"));
        WebUI.setTextFirstVisible(inputQuantity, data.get("QUANTITY"));

        WebUI.clickElement(editorDescription);
        WebUI.sendKeysWithActions(data.get("DESCRIPTION"));

        // Đổi giá / số lượng cũng gọi ajax cập nhật bảng biến thể -> chờ bảng có đủ dòng mới Save (tránh lỗi 500)
        waitForVariantTable(data.get("COLORS").split(","));
        CaptureHelper.takeScreenshotPage("AddProductForm");
        WebUI.clickElement(buttonSaveAndPublish);
        verifyMessage("Product has been inserted successfully");
        return name;
    }

    /** Bật Colors, chọn nhiều màu (Ctrl+A + Delete để xoá từ khoá cũ) rồi verify số lượng */
    @Step("Select colors")
    public void selectColors(String[] colors) {
        WebUI.clickElement(switchColors);
        WebUI.clickElement(dropdownColors);
        WebElement search = WebUI.waitForElementVisible(inputSearchColors);
        for (String raw : colors) {
            String color = raw.trim();
            search.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            search.sendKeys(color);
            // Click đúng option trùng tên (Enter sẽ chọn option đầu tiên, vd "Red" -> "DarkRed")
            WebUI.clickElement(By.xpath("//select[@name='colors[]']/following-sibling::div"
                    + "//li[not(contains(@class,'hidden'))]//span[normalize-space()='" + color + "']"));
        }
        WebUI.pressKey(Keys.ESCAPE);
        // Chọn màu sẽ gọi ajax sinh bảng biến thể -> chờ xong
        WebUI.waitForPageLoaded();

        Select select = new Select(DriverManager.getDriver().findElement(selectColors));
        WebUI.verifyEquals(select.getAllSelectedOptions().size(), colors.length, "Số lượng màu được chọn sai");
        for (String color : colors) {
            // option không có text, tên màu nằm trong data-content: ...<span>Red</span>...
            WebUI.verifyTrue(select.getAllSelectedOptions().stream()
                            .anyMatch(o -> String.valueOf(o.getAttribute("data-content")).contains("<span>" + color.trim() + "</span>")),
                    "Màu chưa được chọn: " + color);
        }
        LogUtils.info("Colors hiển thị: " + WebUI.getElementText(dropdownColors));
        int displayed = WebUI.getElementCount(By.xpath("//select[@name='colors[]']/following-sibling::div//li[contains(@class,'selected')]"));
        WebUI.verifyEquals(displayed, colors.length, "Số lượng màu hiển thị sai");
    }

    /** Bảng biến thể (#sku_combination) sinh bằng ajax: mỗi màu có input price_<Màu> */
    @Step("Wait for variant table")
    public void waitForVariantTable(String[] colors) {
        WebUI.waitForPageLoaded();
        for (String color : colors) {
            WebUI.waitForElementPresent(By.xpath("//div[@id='sku_combination']//input[@name='price_"
                    + color.trim().replace(" ", "") + "']"));
        }
        WebUI.waitForPageLoaded();
    }

    @Step("Search Product {0}")
    public void searchProduct(String name) {
        openProductPage();
        WebUI.setTextAndKey(inputSearch, name, Keys.ENTER);
        WebUI.waitForPageLoaded();
    }

    @Step("Verify Product {0} in table")
    public void verifyProductInTable(String name) {
        searchProduct(name);
        WebUI.waitForTextInElement(firstRowName, name);
        WebUI.verifyContains(WebUI.getElementText(firstRowName), name, "Không tìm thấy product trong bảng");
    }

    /** BÀI TẬP 9: search rồi kiểm tra tất cả dòng cột Name đều chứa từ khoá */
    @Step("Search Product table with keyword {0}")
    public void searchAndCheckTable(String keyword) {
        searchProduct(keyword);
        WebUI.waitForTextInElement(firstRowName, keyword);
        WebUI.checkSearchTableByColumn(tableRows, 2, keyword);
    }

    @Step("Edit Product {0} -> {1}")
    public void editProduct(String oldName, String newName, String newUnitPrice) {
        searchProduct(oldName);
        WebUI.waitForTextInElement(firstRowName, oldName);
        WebUI.clickElement(firstRowEdit);
        WebUI.waitForPageLoaded();
        WebUI.verifyEquals(WebUI.getAttributeElement(inputName, "value"), oldName, "Giá trị Name cũ ở trang Edit sai");
        CaptureHelper.takeScreenshotPage("EditProduct");

        WebUI.setText(inputName, newName);
        WebUI.setText(inputUnitPrice, newUnitPrice);
        WebUI.clickElement(buttonUpdateProduct);
        verifyMessage("Product has been updated successfully");
    }
}
