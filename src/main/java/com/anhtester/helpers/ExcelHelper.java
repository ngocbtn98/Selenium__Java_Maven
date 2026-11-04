package com.anhtester.helpers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * BÀI TẬP 10: Đọc ghi file Excel với Apache POI.
 * Dòng đầu tiên (row 0) của mỗi sheet là tên cột.
 */
public class ExcelHelper {
    private Workbook workbook;
    private Sheet sheet;
    private String excelFilePath;
    private final Map<String, Integer> columns = new HashMap<>();
    private final DataFormatter formatter = new DataFormatter();

    public void setExcelFile(String excelPath, String sheetName) {
        excelFilePath = excelPath.startsWith(SystemHelper.getCurrentDir()) || new File(excelPath).isAbsolute()
                ? excelPath : SystemHelper.getCurrentDir() + excelPath;
        File file = new File(excelFilePath);
        if (!file.exists()) {
            throw new RuntimeException("File Excel không tồn tại: " + excelFilePath);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = WorkbookFactory.create(fis);
        } catch (IOException e) {
            throw new RuntimeException("Không đọc được file Excel: " + excelFilePath, e);
        }
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet không tồn tại: " + sheetName);
        }
        columns.clear();
        Row header = sheet.getRow(0);
        for (Cell cell : header) {
            columns.put(formatter.formatCellValue(cell).trim(), cell.getColumnIndex());
        }
    }

    public String getCellData(int columnIndex, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(columnIndex);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    public String getCellData(String columnName, int rowIndex) {
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new RuntimeException("Cột không tồn tại: " + columnName);
        }
        return getCellData(columnIndex, rowIndex);
    }

    /** Số dòng dữ liệu (không tính header) */
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    public void setCellData(String text, String columnName, int rowIndex) {
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new RuntimeException("Cột không tồn tại: " + columnName);
        }
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }
        cell.setCellValue(text);
        try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Không ghi được file Excel: " + excelFilePath, e);
        }
    }

    /** Lấy dữ liệu dạng Hashtable (key = tên cột) cho DataProvider, từ startRow tới endRow */
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int endRow) {
        setExcelFile(excelPath, sheetName);
        int lastRow = Math.min(endRow, getRowCount());
        int size = Math.max(0, lastRow - startRow + 1);
        Object[][] data = new Object[size][1];
        for (int i = startRow; i <= lastRow; i++) {
            Hashtable<String, String> table = new Hashtable<>();
            for (Map.Entry<String, Integer> column : columns.entrySet()) {
                table.put(column.getKey(), getCellData(column.getValue(), i));
            }
            data[i - startRow][0] = table;
        }
        return data;
    }

    /**
     * Tạo file test data mặc định nếu chưa có (để clone project về là chạy được ngay).
     * Có thể mở file Excel ra sửa data sau khi được tạo.
     */
    public static synchronized void createDefaultTestDataFile(String excelPath) {
        String fullPath = new File(excelPath).isAbsolute() ? excelPath : SystemHelper.getCurrentDir() + excelPath;
        File file = new File(fullPath);
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        try (Workbook wb = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(file)) {
            writeSheet(wb, "Login", new String[][]{
                    {"EMAIL", "PASSWORD", "EXPECTED", "RESULT"},
                    {"admin@example.com", "123456", "SUCCESS", ""},
                    {"admin@example.com", "wrong_pass", "FAIL", ""},
                    {"notexist@example.com", "123456", "FAIL", ""}
            });
            writeSheet(wb, "Category", new String[][]{
                    {"NAME", "ORDER", "IMAGE", "META_TITLE", "META_DESCRIPTION", "NEW_NAME"},
                    {"Category Auto", "1", "brandimg", "Category Auto Meta", "Category auto meta description", "Category Auto Edited"}
            });
            writeSheet(wb, "Product", new String[][]{
                    {"NAME", "CATEGORY", "UNIT", "MIN_QTY", "TAGS", "COLORS", "UNIT_PRICE", "QUANTITY", "DESCRIPTION", "NEW_NAME", "NEW_UNIT_PRICE"},
                    {"Product Auto", "Sport shoes", "Pc", "1", "selenium", "Red,Blue", "150", "100", "Product auto description", "Product Auto Edited", "200"}
            });
            writeSheet(wb, "Brand", new String[][]{
                    {"NAME", "IMAGE", "META_TITLE", "META_DESCRIPTION"},
                    {"Brand Auto", "brandimg", "Brand Auto Meta", "Brand auto meta description"}
            });
            wb.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Không tạo được file test data: " + fullPath, e);
        }
    }

    private static void writeSheet(Workbook wb, String sheetName, String[][] data) {
        Sheet sh = wb.createSheet(sheetName);
        for (int r = 0; r < data.length; r++) {
            Row row = sh.createRow(r);
            for (int c = 0; c < data[r].length; c++) {
                row.createCell(c).setCellValue(data[r][c]);
            }
        }
        for (int c = 0; c < data[0].length; c++) {
            sh.autoSizeColumn(c);
        }
    }
}
