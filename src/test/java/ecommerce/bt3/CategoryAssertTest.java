package ecommerce.bt3;

import ecommerce.LocatorsCMS;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BÀI TẬP 3: Assert cho Add Category (text dialog, text mỗi trang, giá trị item vừa add) - xem CategoryBase.addCategory()
 * BÀI TẬP 3 - PART 2: Edit và Delete category.
 */
public class CategoryAssertTest extends CategoryBase {

    @Test(priority = 1)
    public void testAddCategory() {
        addCategory();
    }

    @Test(priority = 2)
    public void testEditCategory() {
        String oldName = addCategory();
        String newName = oldName + " Edited";

        // Đang ở trang list, dòng đầu là category vừa add -> click Edit
        clickElement(LocatorsCMS.firstRowEdit);
        Assert.assertEquals(getText(LocatorsCMS.headerCategoryInformation), "Category Information", "Header trang Edit sai");
        Assert.assertEquals(waitForElementVisible(LocatorsCMS.inputCategoryName).getAttribute("value"), oldName,
                "Giá trị Name cũ ở trang Edit sai");

        setText(LocatorsCMS.inputCategoryName, newName);
        setText(LocatorsCMS.inputMetaTitle, newName);
        clickElement(LocatorsCMS.buttonSave);
        Assert.assertEquals(getText(LocatorsCMS.messageAlert), "Category has been updated successfully",
                "Message cập nhật category sai");

        // Search lại kiểm tra giá trị mới
        verifyCategoryInList(newName);
    }

    @Test(priority = 3)
    public void testDeleteCategory() {
        String name = addCategory();

        clickElement(LocatorsCMS.firstRowDelete);
        Assert.assertEquals(getText(LocatorsCMS.headerDeleteConfirm), "Delete Confirmation", "Header dialog Delete sai");
        Assert.assertTrue(getText(LocatorsCMS.messageDeleteConfirm).contains("Are you sure to delete this"),
                "Nội dung dialog Delete sai");
        clickElement(LocatorsCMS.buttonConfirmDelete);
        Assert.assertEquals(getText(LocatorsCMS.messageAlert), "Category has been deleted successfully",
                "Message xoá category sai");

        // Search lại: element không còn tồn tại
        searchCategory(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorsCMS.nothingFound)));
        Assert.assertTrue(driver.findElements(
                        By.xpath("//table//tbody//td[normalize-space()='" + name + "']")).isEmpty(),
                "Category vẫn còn tồn tại sau khi xoá");
    }
}
