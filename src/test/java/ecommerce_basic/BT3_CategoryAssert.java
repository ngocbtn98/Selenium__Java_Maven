package ecommerce_basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static ecommerce_basic.LocatorsCMS.*;

/**
 * BÀI TẬP 3 + 3 MỞ RỘNG: Assert cho Add Category, thêm case Edit và Delete.
 */
public class BT3_CategoryAssert extends BT2_AddCategoryTestNG {

    private void addCategoryWithAssert(String categoryName) {
        openCategoryPage();
        Assert.assertEquals(getText(headerAllCategories), "All categories", "Header trang Category không đúng");
        click(buttonAddNewCategory);
        Assert.assertEquals(getText(headerCategoryInformation), "Category Information", "Header trang Add Category không đúng");

        type(inputName, categoryName);
        type(inputOrderLevel, "1");
        click(dropdownType);
        click("//button[@data-id='digital']/following-sibling::div//span[normalize-space()='Physical']");

        // Kiểm tra dialog chọn hình
        click(buttonBrowseBanner);
        Assert.assertTrue(waitVisible("//div[@id='aizUploaderModal']//h5 | //div[@id='aizUploaderModal']//a[normalize-space()='Select File']").isDisplayed(),
                "Dialog Upload không hiển thị");
        Assert.assertEquals(getText(buttonAddFiles), "Add Files", "Text button dialog upload không đúng");
        type(inputSearchUploader, IMAGE_NAME, Keys.ENTER);
        click(firstImageUploader);
        click(buttonAddFiles);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(buttonAddFiles)));
        selectImage(buttonBrowseIcon);

        type(inputMetaTitle, "Meta " + categoryName);
        type(textareaMetaDescription, "Description for " + categoryName);
        click(buttonSave);

        // Kiểm tra text thông báo
        Assert.assertEquals(getText(messageToast), "Category has been inserted successfully", "Message add category không đúng");
    }

    private void verifyCategoryInTable(String categoryName) {
        searchCategory(categoryName);
        Assert.assertEquals(getText(firstRowName), categoryName, "Category trong table không đúng");
    }

    @Test(priority = 1)
    public void testAddCategory() {
        String categoryName = "Category BT3 " + System.currentTimeMillis();
        addCategoryWithAssert(categoryName);
        verifyCategoryInTable(categoryName);
    }

    @Test(priority = 2)
    public void testEditCategory() {
        String categoryName = "Category BT3 Edit " + System.currentTimeMillis();
        String newName = categoryName + " Updated";
        addCategoryWithAssert(categoryName);
        verifyCategoryInTable(categoryName);

        click(firstRowEdit);
        Assert.assertEquals(getText(headerCategoryInformation), "Category Information");
        Assert.assertEquals(waitVisible(inputName).getAttribute("value"), categoryName, "Giá trị Name ở form Edit không đúng");
        type(inputName, newName);
        click(buttonSave);
        Assert.assertEquals(getText(messageToast), "Category has been updated successfully");

        verifyCategoryInTable(newName);
    }

    @Test(priority = 3)
    public void testDeleteCategory() {
        String categoryName = "Category BT3 Delete " + System.currentTimeMillis();
        addCategoryWithAssert(categoryName);
        verifyCategoryInTable(categoryName);

        click(firstRowDelete);
        Assert.assertEquals(getText(headerDeleteModal), "Delete Confirmation", "Header dialog delete không đúng");
        Assert.assertEquals(getText(messageDeleteModal), "Are you sure to delete this?", "Message dialog delete không đúng");
        click(buttonConfirmDelete);
        Assert.assertEquals(getText(messageToast), "Category has been deleted successfully");

        // Search lại => element không còn tồn tại
        openCategoryPage();
        type(inputSearch, categoryName, Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(inputSearch), categoryName));
        boolean exists = !driver.findElements(By.xpath("//table//tbody//td[normalize-space()='" + categoryName + "']")).isEmpty();
        Assert.assertFalse(exists, "Category vẫn còn tồn tại sau khi xoá");
    }
}
