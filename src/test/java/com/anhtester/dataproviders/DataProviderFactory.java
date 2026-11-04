package com.anhtester.dataproviders;

import com.anhtester.helpers.ExcelHelper;
import com.anhtester.helpers.PropertiesHelper;
import org.testng.annotations.DataProvider;

/**
 * BÀI TẬP 10: Toàn bộ data test (Login, Brand, Category, Product) đọc từ file Excel.
 * Đường dẫn file lấy từ config.properties (BT11).
 */
public class DataProviderFactory {

    private static Object[][] read(String sheetName, int startRow, int endRow) {
        String path = PropertiesHelper.getValue("excel.data.path");
        ExcelHelper.createDefaultTestDataFile(path);
        return new ExcelHelper().getDataHashTable(path, sheetName, startRow, endRow);
    }

    @DataProvider(name = "data_login")
    public static Object[][] dataLogin() {
        return read("Login", 1, Integer.MAX_VALUE);
    }

    @DataProvider(name = "data_category")
    public static Object[][] dataCategory() {
        return read("Category", 1, 1);
    }

    @DataProvider(name = "data_product")
    public static Object[][] dataProduct() {
        return read("Product", 1, 1);
    }

    /** BT4-Part 2: luồng Category -> Product cần data của cả 2 sheet */
    @DataProvider(name = "data_category_product")
    public static Object[][] dataCategoryProduct() {
        return new Object[][]{{dataCategory()[0][0], dataProduct()[0][0]}};
    }

    @DataProvider(name = "data_brand")
    public static Object[][] dataBrand() {
        return read("Brand", 1, 1);
    }
}
