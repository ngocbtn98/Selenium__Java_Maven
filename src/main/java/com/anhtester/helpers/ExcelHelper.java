package com.anhtester.helpers;

import com.anhtester.utils.LogUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/** BT10: Đọc ghi file Excel với Apache POI */
public class ExcelHelper {
    private Workbook workbook;
    private Sheet sheet;
    private String excelFilePath;
    private final Map<String, Integer> columns = new HashMap<>();
    private final DataFormatter formatter = new DataFormatter();

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            File file = new File(excelPath);
            if (!file.exists()) {
                throw new RuntimeException("File Excel không tồn tại: " + excelPath);
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fis);
            }
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet không tồn tại: " + sheetName);
            }
            excelFilePath = excelPath;
            columns.clear();
            sheet.getRow(0).forEach(cell -> columns.put(cell.getStringCellValue().trim(), cell.getColumnIndex()));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCellData(int columnIndex, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";
        Cell cell = row.getCell(columnIndex);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    public String getCellData(String columnName, int rowIndex) {
        Integer col = columns.get(columnName);
        if (col == null) {
            throw new RuntimeException("Không có cột: " + columnName);
        }
        return getCellData(col, rowIndex);
    }

    public void setCellData(String text, String columnName, int rowIndex) {
        try {
            Row row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);
            Cell cell = row.getCell(columns.get(columnName));
            if (cell == null) cell = row.createCell(columns.get(columnName));
            cell.setCellValue(text);
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            LogUtils.error("Không ghi được Excel: " + e.getMessage());
        }
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    /** Đọc dữ liệu từ dòng startRow đến endRow, mỗi dòng là một Hashtable (column -> value) cho DataProvider */
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int endRow) {
        setExcelFile(excelPath, sheetName);
        int last = Math.min(endRow, getRowCount());
        Object[][] data = new Object[last - startRow + 1][1];
        for (int i = startRow; i <= last; i++) {
            Hashtable<String, String> table = new Hashtable<>();
            for (Map.Entry<String, Integer> col : columns.entrySet()) {
                table.put(col.getKey(), getCellData(col.getValue(), i));
            }
            data[i - startRow][0] = table;
        }
        return data;
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
