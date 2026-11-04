package ecommerce_basic;

import org.testng.annotations.Test;

import static ecommerce_basic.LocatorsCMS.firstRowName;

/**
 * BÀI TẬP 2: Tách code BT1 theo annotation TestNG (không dùng hàm main).
 * - Setup driver + Login chạy trước mỗi test case: xem BT2_BaseSetup (@BeforeMethod)
 * - Chạy bằng suite: src/test/resources/suites/BT2_Suite.xml
 */
public class BT2_AddCategoryTestNG extends BT2_BaseSetup {

    @Test
    public void testAddCategory() {
        String categoryName = "Category BT2 " + System.currentTimeMillis();
        addCategory(categoryName);
        searchCategory(categoryName);
        String actual = getText(firstRowName);
        System.out.println("Category name: " + actual);
        if (!actual.equals(categoryName)) {
            throw new RuntimeException("Category không đúng. Actual: " + actual);
        }
    }
}
