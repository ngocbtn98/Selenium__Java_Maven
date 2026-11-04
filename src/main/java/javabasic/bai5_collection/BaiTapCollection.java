package javabasic.bai5_collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * BT-JAVA BASIC 5: Collection kết hợp tạo hàm riêng.
 */
public class BaiTapCollection {

    // Xử lý lại bài tập Java Basic 4 với ArrayList
    public static ArrayList<Integer> laySoChan(int from, int to) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            if (i % 2 == 0) {
                list.add(i);
            }
        }
        return list;
    }

    // Hàm riêng thêm thông tin cơ bản của một nhân viên vào collection (HashMap)
    public static Map<String, String> themNhanVien(String maNV, String hoTen, int tuoi, String phongBan, String email) {
        Map<String, String> nhanVien = new HashMap<>();
        nhanVien.put("Mã NV", maNV);
        nhanVien.put("Họ tên", hoTen);
        nhanVien.put("Tuổi", String.valueOf(tuoi));
        nhanVien.put("Phòng ban", phongBan);
        nhanVien.put("Email", email);
        return nhanVien;
    }

    public static void main(String[] args) {
        ArrayList<Integer> soChan = laySoChan(0, 50);
        System.out.println("Các số chẵn (ArrayList):");
        for (int i = 0; i < soChan.size(); i++) {
            System.out.print(soChan.get(i) + " ");
        }
        System.out.println();

        Map<String, String> nhanVien = themNhanVien("NV001", "Trần Thị B", 25, "QA", "b.tran@example.com");
        System.out.println("Thông tin nhân viên:");
        for (Map.Entry<String, String> entry : nhanVien.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Danh sách nhiều nhân viên
        ArrayList<Map<String, String>> danhSach = new ArrayList<>();
        danhSach.add(nhanVien);
        danhSach.add(themNhanVien("NV002", "Lê Văn C", 30, "Dev", "c.le@example.com"));
        System.out.println("Danh sách nhân viên:");
        for (int i = 0; i < danhSach.size(); i++) {
            System.out.println((i + 1) + ". " + danhSach.get(i).get("Họ tên") + " - " + danhSach.get(i).get("Phòng ban"));
        }
    }
}
