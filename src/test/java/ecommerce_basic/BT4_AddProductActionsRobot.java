package ecommerce_basic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import static ecommerce_basic.LocatorsCMS.*;

/**
 * BÀI TẬP 4: Actions + Robot class - Add Product.
 * BÀI TẬP 4 MỞ RỘNG: Luồng liên tục Add Category -> Add Product (dùng lại category vừa add).
 */
public class BT4_AddProductActionsRobot extends BT2_BaseSetup {

    // Biến toàn cục lưu data để dễ chỉnh sửa khi chạy lại
    public static String CATEGORY_NAME = "Category BT4 " + System.currentTimeMillis();
    public static String PRODUCT_NAME = "Product BT4 " + System.currentTimeMillis();
    public static String EXISTING_CATEGORY = "Women Clothing & Fashion";
    public static String UNIT = "pc";
    public static String MIN_QTY = "1";
    public static String TAG = "selenium";
    public static String[] COLORS = {"Black", "Blue", "Red"};
    public static String UNIT_PRICE = "100";
    public static String DISCOUNT = "5";
    public static String QUANTITY = "50";

    private void selectCategory(String categoryName) {
        click(dropdownCategory);
        type(inputSearchCategory, categoryName);
        click("//button[@data-id='category_id']/following-sibling::div//span[normalize-space()='" + categoryName + "']");
    }

    /** Chọn nhiều màu: dùng Actions gõ tên màu + Enter, rồi Ctrl + A và Delete để xoá ô search trước khi gõ màu tiếp theo */
    private void selectColors(String... colors) {
        Actions actions = new Actions(driver);
        click(toggleColors);
        click(dropdownColors);
        WebElement search = waitVisible(inputSearchColors);
        for (String color : colors) {
            actions.click(search).sendKeys(color).pause(500).sendKeys(Keys.ENTER).perform();
            // Ctrl + A và Delete để xoá text trong ô search
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
        }
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    /** Dùng Robot class để nhấn phím (Ctrl + A, Delete) xoá text trong ô đang focus */
    private void clearByRobot(String xpath) throws AWTException {
        click(xpath);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
    }

    private void addProduct(String productName, String categoryName) throws AWTException {
        click(menuProducts);
        click(menuAddNewProduct);
        Assert.assertEquals(getText(headerAddNewProduct), "Add New Product");

        type(inputProductName, productName);
        selectCategory(categoryName);
        type(inputUnit, UNIT);

        // Min qty có giá trị mặc định 1 => dùng Robot xoá rồi nhập lại
        clearByRobot(inputMinQty);
        waitVisible(inputMinQty).sendKeys(MIN_QTY);

        new Actions(driver).click(waitVisible(inputTags)).sendKeys(TAG).sendKeys(Keys.ENTER).perform();

        selectColors(COLORS);
        // Verify số lượng color được chọn
        int selectedCount = new Select(driver.findElement(By.xpath(selectColors))).getAllSelectedOptions().size();
        Assert.assertEquals(selectedCount, COLORS.length, "Số lượng color được chọn không đúng");
        String colorText = getText(textSelectedColors);
        System.out.println("Colors hiển thị: " + colorText);
        if (colorText.contains("selected")) {
            Assert.assertTrue(colorText.startsWith(String.valueOf(COLORS.length)), "Số lượng color hiển thị không đúng");
        } else {
            Assert.assertEquals(colorText.split(",").length, COLORS.length, "Số lượng color hiển thị không đúng");
        }

        WebElement price = waitVisible(inputUnitPrice);
        new Actions(driver).scrollToElement(price).perform();
        type(inputUnitPrice, UNIT_PRICE);
        type(inputDiscount, DISCOUNT);
        if (!driver.findElements(By.xpath(inputQuantity)).isEmpty()) {
            type(inputQuantity, QUANTITY);
        }

        click(buttonSavePublish);
        Assert.assertEquals(getText(messageToast), "Product has been inserted successfully");
    }

    private void verifyProduct(String productName) {
        click(menuProducts);
        click(menuAllProducts);
        type(inputSearch, productName, Keys.ENTER);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(firstRowName), productName));
        Assert.assertTrue(getText(firstRowName).contains(productName), "Product không có trong danh sách");
    }

    @Test(priority = 1, description = "BT4: Add Product với category có sẵn")
    public void testAddProduct() throws AWTException {
        String productName = PRODUCT_NAME + " A";
        addProduct(productName, EXISTING_CATEGORY);
        verifyProduct(productName);
    }

    @Test(priority = 2, description = "BT4 mở rộng: Add Category -> Add Product dùng category vừa add")
    public void testAddCategoryThenProduct() throws AWTException {
        addCategory(CATEGORY_NAME);
        Assert.assertEquals(getText(messageToast), "Category has been inserted successfully");
        searchCategory(CATEGORY_NAME);
        // Lấy giá trị category vừa add từ table, không điền tay
        String categoryFromTable = getText(firstRowName);

        String productName = PRODUCT_NAME + " B";
        addProduct(productName, categoryFromTable);
        verifyProduct(productName);
    }
}
