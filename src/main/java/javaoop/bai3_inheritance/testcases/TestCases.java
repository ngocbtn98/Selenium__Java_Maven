package javaoop.bai3_inheritance.testcases;

import javaoop.bai3_inheritance.common.BaseTest;

public class TestCases extends BaseTest {

    public void testLogin() {
        createDriver();
        System.out.println("Step 1: Mở trang https://cms.anhtester.com/login");
        System.out.println("Step 2: Nhập email admin@example.com");
        System.out.println("Step 3: Nhập password 123456");
        System.out.println("Step 4: Click button Login");
        System.out.println("Step 5: Kiểm tra trang Dashboard hiển thị");
        closeDriver(getBrowser());
    }

    public void testAddCategory() {
        createDriver();
        System.out.println("Step 1: Đăng nhập với admin@example.com/123456");
        System.out.println("Step 2: Click menu Products > Category");
        System.out.println("Step 3: Click button Add New category");
        System.out.println("Step 4: Nhập Name, Ordering Number, chọn Type, Banner, Icon");
        System.out.println("Step 5: Click button Save");
        System.out.println("Step 6: Kiểm tra thông báo Category has been inserted successfully");
        closeDriver(getBrowser());
    }

    public static void main(String[] args) {
        TestCases testCases = new TestCases();
        System.out.println("===== TC Login =====");
        testCases.testLogin();
        System.out.println("===== TC Add Category =====");
        testCases.testAddCategory();
    }
}
