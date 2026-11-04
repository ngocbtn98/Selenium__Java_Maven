package ecommerce.bt4;

import ecommerce.LocatorsCMS;
import ecommerce.bt3.CategoryBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * BÀI TẬP 4: Add Product vận dụng Actions + Robot class.
 * BÀI TẬP 4 - PART 2: Luồng liên tục Add Category -> Add Product (dùng lại category vừa add).
 */
public class AddProductTest extends CategoryBase {

    //===== Biến toàn cục lưu Data test, sửa ở đây khi chạy lại nhiều lần =====
    public static String CATEGORY_NAME = "Demo Category";        // category có sẵn (BT3)
    public static String PRODUCT_NAME_PREFIX = "Product BT4 ";
    public static String UNIT = "Pc";
    public static String MIN_QTY = "1";
    public static String[] COLORS = {"Red", "Blue", "Green"};    // >= 2 màu
    public static String UNIT_PRICE = "150";
    public static String QUANTITY = "100";

    @Test(priority = 1)
    public void testAddProduct() throws AWTException {
        addProduct(CATEGORY_NAME);
    }

    @Test(priority = 2)
    public void testAddCategoryThenAddProduct() throws AWTException {
        // Không điền category bằng tay: lấy từ kết quả add category
        String newCategory = addCategory();
        addProduct(newCategory);
    }

    public String addProduct(String categoryName) throws AWTException {
        String productName = PRODUCT_NAME_PREFIX + System.currentTimeMillis();
        Actions actions = new Actions(driver);
        Robot robot = new Robot();

        openProductsSubMenu(LocatorsCMS.menuAddNewProduct);
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/products/create"), "Không vào được trang Add New Product");

        setText(LocatorsCMS.inputProductName, productName);

        // Chọn Category: search + Enter
        clickElement(LocatorsCMS.dropdownCategory);
        setText(LocatorsCMS.inputSearchCategory, categoryName);
        actions.sendKeys(Keys.ENTER).perform();
        Assert.assertTrue(getText(LocatorsCMS.dropdownCategory).contains(categoryName), "Chọn Category sai");

        setText(LocatorsCMS.inputUnit, UNIT);
        setText(LocatorsCMS.inputMinQty, MIN_QTY);

        // Tags: gõ rồi Enter bằng Robot
        clickElement(LocatorsCMS.inputTags);
        actions.sendKeys("selenium").perform();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // ===== Colors =====
        WebElement colorsSwitch = driver.findElement(By.xpath(LocatorsCMS.switchColors));
        actions.moveToElement(colorsSwitch).click().perform();
        clickElement(LocatorsCMS.dropdownColors);
        WebElement searchColors = waitForElementVisible(LocatorsCMS.inputSearchColors);
        for (String color : COLORS) {
            searchColors.click();
            // Ctrl + A và Delete để xoá từ khoá màu cũ trong ô search
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            robot.keyPress(KeyEvent.VK_DELETE);
            robot.keyRelease(KeyEvent.VK_DELETE);
            actions.sendKeys(color).perform();
            waitForElementVisible("//select[@name='colors[]']/following-sibling::div//li[not(contains(@class,'hidden'))]//span[normalize-space()='" + color + "']");
            actions.sendKeys(Keys.ENTER).perform();
        }
        actions.sendKeys(Keys.ESCAPE).perform();

        // Verify số lượng màu được chọn
        Select selectColors = new Select(driver.findElement(By.xpath(LocatorsCMS.selectColors)));
        Assert.assertEquals(selectColors.getAllSelectedOptions().size(), COLORS.length, "Số lượng màu được chọn sai");
        String colorsText = getText(LocatorsCMS.dropdownColors);
        System.out.println("Colors hiển thị: " + colorsText);
        Assert.assertEquals(colorsText.split(",").length, COLORS.length, "Số lượng màu hiển thị sai");

        // Giá và số lượng
        setText(LocatorsCMS.inputUnitPrice, UNIT_PRICE);
        WebElement quantity = driver.findElements(By.xpath(LocatorsCMS.inputQuantity)).stream()
                .filter(WebElement::isDisplayed).findFirst().orElse(null);
        if (quantity != null) {
            quantity.clear();
            quantity.sendKeys(QUANTITY);
        }

        // Description (summernote) - double click + gõ
        WebElement description = driver.findElement(By.xpath(LocatorsCMS.editorDescription));
        actions.moveToElement(description).click().sendKeys("Sản phẩm demo BT4: " + productName).perform();

        clickElement(LocatorsCMS.buttonSaveAndPublish);
        Assert.assertEquals(getText(LocatorsCMS.messageAlert), "Product has been inserted successfully",
                "Message thêm product sai");

        // Kiểm tra trong danh sách sản phẩm
        openProductsSubMenu(LocatorsCMS.menuAllProducts);
        setText(LocatorsCMS.inputSearch, productName, Keys.ENTER);
        waitForTextInElement(LocatorsCMS.firstRowProductName, productName);
        Assert.assertTrue(getText(LocatorsCMS.firstRowProductName).contains(productName), "Không tìm thấy product vừa add");
        return productName;
    }
}
