package javabasic.bt5;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BT-JAVA BASIC 5 (phần 2): Hàm riêng thêm thông tin cơ bản của nhân viên vào collection (Map key - value).
 */
public class NhanVienCollection {

    public static Map<String, String> themNhanVien(String maNV, String hoTen, int tuoi, String phongBan, double luong) {
        // LinkedHashMap giữ đúng thứ tự thêm vào
        Map<String, String> nhanVien = new LinkedHashMap<>();
        nhanVien.put("Mã NV", maNV);
        nhanVien.put("Họ tên", hoTen);
        nhanVien.put("Tuổi", String.valueOf(tuoi));
        nhanVien.put("Phòng ban", phongBan);
        nhanVien.put("Lương", String.valueOf(luong));
        return nhanVien;
    }

    public static void main(String[] args) {
        Map<String, String> nhanVien = themNhanVien("NV001", "Trần Thị B", 25, "QA/QC", 15000000);

        System.out.println("Thông tin nhân viên:");
        for (Map.Entry<String, String> entry : nhanVien.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
