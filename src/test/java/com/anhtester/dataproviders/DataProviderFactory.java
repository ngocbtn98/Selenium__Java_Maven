package com.anhtester.dataproviders;

import com.anhtester.constants.ConfigData;
import com.anhtester.helpers.ExcelHelper;
import com.anhtester.helpers.SystemHelper;
import org.testng.annotations.DataProvider;

/** BT10: Data test đọc từ Excel file */
public class DataProviderFactory {

    private static Object[][] read(String sheet, int from, int to) {
        return new ExcelHelper().getDataHashTable(SystemHelper.getCurrentDir() + ConfigData.EXCEL_DATA_FILE(), sheet, from, to);
    }

    @DataProvider(name = "data_login_success")
    public Object[][] dataLoginSuccess() {
        return read("Login", 1, 1);
    }

    @DataProvider(name = "data_login_fail")
    public Object[][] dataLoginFail() {
        return read("Login", 2, 3);
    }

    @DataProvider(name = "data_category")
    public Object[][] dataCategory() {
        return read("Category", 1, 1);
    }

    @DataProvider(name = "data_product")
    public Object[][] dataProduct() {
        return read("Product", 1, 1);
    }

    @DataProvider(name = "data_brand")
    public Object[][] dataBrand() {
        return read("Brand", 1, 1);
    }
}
