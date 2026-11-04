package javaoop.bt3.testcases;

import javaoop.bt3.common.BaseTest;

/**
 * BT-JAVA OOP 3: Kế thừa BaseTest, viết 2 TC Login và Add Category (https://cms.anhtester.com/login).
 */
public class TestCases extends BaseTest {

    public void testLogin() {
        createDriver();
        System.out.println("Step 1: Mở trang https://cms.anhtester.com/login");
        System.out.println("Step 2: Nhập email: admin@example.com");
        System.out.println("Step 3: Nhập password: 123456");
        System.out.println("Step 4: Click button Login");
        System.out.println("Step 5: Kiểm tra chuyển tới trang Dashboard thành công");
        closeDriver(getBrowser());
    }

    public void testAddCategory() {
        createDriver();
        System.out.println("Step 1: Đăng nhập với admin@example.com/123456");
        System.out.println("Step 2: Click menu Products");
        System.out.println("Step 3: Click menu Category");
        System.out.println("Step 4: Click button Add New category");
        System.out.println("Step 5: Nhập Name, chọn Parent Category, Ordering Number, Type");
        System.out.println("Step 6: Chọn Banner, Icon, nhập Meta Title, Meta Description");
        System.out.println("Step 7: Click button Save");
        System.out.println("Step 8: Kiểm tra thông báo 'Category has been inserted successfully'");
        closeDriver(getBrowser());
    }

    public static void main(String[] args) {
        TestCases testCases = new TestCases();
        testCases.testLogin();
        System.out.println();

        // Đổi browser qua hàm set để chạy TC thứ 2
        testCases.setBrowser("Edge");
        testCases.testAddCategory();
    }
}
