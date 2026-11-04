# Selenium__Java_Maven
Project Selenium Java với Maven: lời giải các bài tập khoá **Selenium Java - Anh Tester** (file *BÀI TẬP SELENIUM JAVA ANHTESTER 2024*).

## Mục lục bài tập

| Bài tập | Vị trí |
|---|---|
| Java Basic 1 - Variables | `src/main/java/javabasic/bai1_variables` (SinhVien, ThongTin) |
| Java Basic 2 - Method, Data type | `src/main/java/javabasic/bai2_method/Calculator.java` |
| Java Basic 3 - Toán tử, IF ELSE | `src/main/java/javabasic/bai3_operators/ToanTu.java` |
| Java Basic 4 - For, Mảng | `src/main/java/javabasic/bai4_for_array/SoChan.java` |
| Java Basic 5 - Collection | `src/main/java/javabasic/bai5_collection/BaiTapCollection.java` |
| Java OOP 1 - Object Class | `src/main/java/javaoop/bai1_objectclass` |
| Java OOP 2 - Package, Access modifier | `src/main/java/javaoop/bai2_package` (person, company) |
| Java OOP 3 - Kế thừa, Đóng gói | `src/main/java/javaoop/bai3_inheritance` (common, testcases) |
| BT Locators (CRM) | `src/test/java/Bai5_locators/LocatorsCRM.java` |
| BT WebElement - Add New Customer | `src/test/java/Bai6_WebElement/AddNewCustomer.java` |
| BT WebDriver - New Project | `src/test/java/Bai7_WebDriver/AddNewProject.java` |
| BT1 - Add Category (main) | `src/test/java/ecommerce_basic/BT1_AddCategory.java` |
| BT2 - TestNG annotation | `src/test/java/ecommerce_basic/BT2_AddCategoryTestNG.java` + `suites/BT2_Suite.xml` |
| BT3 - Assert, Edit, Delete | `src/test/java/ecommerce_basic/BT3_CategoryAssert.java` |
| BT4 - Actions, Robot, Add Product | `src/test/java/ecommerce_basic/BT4_AddProductActionsRobot.java` |
| BT5, BT6 - POM | `src/test/java/com/anhtester/pages`, `testcases`, `suites/BT5_*.xml`, `suites/BT6_*.xml` |
| BT7, BT8 - Common library, multi browser | `src/main/java/com/anhtester/keywords/WebUI.java`, `common/BaseTest.java`, `suites/BT8_MultiBrowserSuite.xml` |
| BT9 - Web table | `WebUI.checkContainsSearchTableByColumn`, `BasePage.searchDataTable` |
| BT10 - Excel | `helpers/ExcelHelper.java`, `dataproviders/DataProviderFactory.java`, `resources/testdata/DataTest.xlsx` |
| BT11 - Properties | `helpers/PropertiesHelper.java`, `constants/ConfigData.java`, `resources/configs/config.properties` |
| BT12 - Screenshot, Video | `helpers/CaptureHelper.java` |
| BT13 - TestListener | `listeners/TestListener.java` |
| BT14 - Log4j2 | `utils/LogUtils.java`, `resources/log4j2.properties` |
| BT15 - Extent Report | `reports/ExtentReportManager.java`, `reports/ExtentTestManager.java` |
| BT16 - Allure Report | `reports/AllureManager.java`, `@Step` trong `WebUI` |
| BT17 - Github | repo này |

## Chuẩn bị
- Upload sẵn vài hình bằng tay trên https://cms.anhtester.com/admin (Uploaded Files), đặt tên chứa `anhtester`
  (hoặc đổi `image_name` trong `config.properties`).
- Category `Women Clothing & Fashion` dùng cho Product (đổi trong sheet `Product` của `DataTest.xlsx` nếu cần).

## Chạy test
```bash
mvn clean test                                                         # RegressionSuite (mặc định)
mvn clean test -DsuiteXmlFile=src/test/resources/suites/BT2_Suite.xml  # BT2 - BT4
mvn clean test -Dbrowser=firefox -Dheadless=true                       # override config
```
Kết quả: `exports/logs`, `exports/screenshots`, `exports/ExtentReport/ExtentReport.html`.

Allure report: `mvn allure:serve`
