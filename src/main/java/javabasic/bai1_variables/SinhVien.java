package javabasic.bai1_variables;

/**
 * BT-JAVA BASIC 1: Khai báo 3 loại biến (local, instance, static) và in ra ở hàm main.
 */
public class SinhVien {
    // Biến instance (thuộc về đối tượng)
    String hoTen = "Nguyễn Văn A";
    int tuoi = 20;

    // Biến static (thuộc về class)
    static String quocTich = "Việt Nam";

    public static void main(String[] args) {
        // Biến local (chỉ dùng trong hàm main)
        String maSinhVien = "SV001";

        SinhVien sv = new SinhVien();

        System.out.println("===== Biến local =====");
        System.out.println("Mã sinh viên: " + maSinhVien);

        System.out.println("===== Biến instance =====");
        System.out.println("Họ tên: " + sv.hoTen);
        System.out.println("Tuổi: " + sv.tuoi);

        System.out.println("===== Biến static =====");
        System.out.println("Quốc tịch: " + quocTich);

        System.out.println("===== Biến static từ class ThongTin =====");
        System.out.println("Trường: " + ThongTin.tenTruong);
        System.out.println("Lớp: " + ThongTin.tenLop);
        System.out.println("Năm học: " + ThongTin.namHoc);
    }
}
