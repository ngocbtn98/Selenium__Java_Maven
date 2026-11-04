package ecommerce.bt2;

import ecommerce.LocatorsCMS;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

/**
 * BÀI TẬP 2: Add Category viết lại theo TestNG (không dùng hàm main).
 * Chạy bằng suite: suites/BT2_AddCategory.xml
 */
public class AddCategoryTest extends BaseTest {
    public static String IMAGE_NAME = "category"; // tên hình đã upload sẵn bằng tay

    @Test
    public void testAddCategory() {
        String categoryName = "Category BT2 " + System.currentTimeMillis();

        openProductsSubMenu(LocatorsCMS.menuCategory);
        clickElement(LocatorsCMS.buttonAddNewCategory);

        setText(LocatorsCMS.inputCategoryName, categoryName);
        setText(LocatorsCMS.inputOrderingNumber, "1");
        chooseImage(LocatorsCMS.buttonBrowseBanner, IMAGE_NAME);
        chooseImage(LocatorsCMS.buttonBrowseIcon, IMAGE_NAME);
        setText(LocatorsCMS.inputMetaTitle, categoryName);
        setText(LocatorsCMS.textareaMetaDescription, "Meta description " + categoryName);
        clickElement(LocatorsCMS.buttonSave);
        System.out.println("Message: " + getText(LocatorsCMS.messageAlert));

        openProductsSubMenu(LocatorsCMS.menuCategory);
        setText(LocatorsCMS.inputSearch, categoryName, Keys.ENTER);
        waitForTextInElement(LocatorsCMS.firstRowName, categoryName);

        String actualName = getText(LocatorsCMS.firstRowName);
        System.out.println("Category name sau khi search: " + actualName);
        if (actualName.equals(categoryName)) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }
    }
}
