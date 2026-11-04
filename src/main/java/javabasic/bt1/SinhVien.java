package javabasic.bt1;

/**
 * BT-JAVA BASIC 1: khai báo 3 loại biến (local, instance, static) và in ra ở hàm main.
 */
public class SinhVien {
    // Biến static: thuộc về class, dùng chung cho mọi đối tượng
    static String lop = "Selenium Java K1";

    // Biến instance: thuộc về từng đối tượng
    String hoTen = "Nguyễn Văn A";
    int tuoi = 20;

    public static void main(String[] args) {
        // Biến local: chỉ tồn tại trong hàm main
        String maSinhVien = "SV001";

        SinhVien sv = new SinhVien();

        System.out.println("Mã sinh viên (local): " + maSinhVien);
        System.out.println("Họ tên (instance): " + sv.hoTen);
        System.out.println("Tuổi (instance): " + sv.tuoi);
        System.out.println("Lớp (static): " + SinhVien.lop);

        // Gọi biến static từ class ThongTin
        System.out.println("Trường: " + ThongTin.truong);
        System.out.println("Khoa: " + ThongTin.khoa);
        System.out.println("Năm nhập học: " + ThongTin.namNhapHoc);
    }
}
