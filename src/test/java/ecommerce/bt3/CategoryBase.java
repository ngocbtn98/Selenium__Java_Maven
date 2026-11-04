package ecommerce.bt3;

import ecommerce.LocatorsCMS;
import ecommerce.bt2.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.Assert;

/**
 * Các bước xử lý Category dùng lại cho BT3 và BT4-Part 2 (không chứa @Test).
 */
public abstract class CategoryBase extends BaseTest {
    public static String IMAGE_NAME = "category"; // tên hình đã upload sẵn bằng tay

    /** Add category, trả về tên category vừa add */
    public String addCategory() {
        String categoryName = "Category " + System.currentTimeMillis();

        openProductsSubMenu(LocatorsCMS.menuCategory);
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/categories"), "Không vào được trang Category");
        Assert.assertEquals(getText(LocatorsCMS.headerAllCategories).toLowerCase(), "all categories", "Header trang Category sai");

        clickElement(LocatorsCMS.buttonAddNewCategory);
        Assert.assertEquals(getText(LocatorsCMS.headerCategoryInformation), "Category Information", "Header trang Add Category sai");

        setText(LocatorsCMS.inputCategoryName, categoryName);
        setText(LocatorsCMS.inputOrderingNumber, "1");
        chooseImage(LocatorsCMS.buttonBrowseBanner, IMAGE_NAME);
        chooseImage(LocatorsCMS.buttonBrowseIcon, IMAGE_NAME);
        setText(LocatorsCMS.inputMetaTitle, categoryName);
        setText(LocatorsCMS.textareaMetaDescription, "Meta description " + categoryName);
        clickElement(LocatorsCMS.buttonSave);

        Assert.assertEquals(getText(LocatorsCMS.messageAlert), "Category has been inserted successfully",
                "Message thêm category sai");

        verifyCategoryInList(categoryName);
        return categoryName;
    }

    public void searchCategory(String name) {
        openProductsSubMenu(LocatorsCMS.menuCategory);
        setText(LocatorsCMS.inputSearch, name, Keys.ENTER);
    }

    public void verifyCategoryInList(String name) {
        searchCategory(name);
        waitForTextInElement(LocatorsCMS.firstRowName, name);
        Assert.assertEquals(getText(LocatorsCMS.firstRowName), name, "Tên category trong bảng không đúng");
    }
}
