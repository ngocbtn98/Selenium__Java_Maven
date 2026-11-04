package javaoop.bt1;

/**
 * BT-JAVA OOP 1: Class ThongTin gọi lại thông tin nhân viên từ class NhanVien.
 */
public class ThongTin {
    public static void main(String[] args) {
        NhanVien nv1 = new NhanVien("NV001", "Nguyễn Văn A", 25, "Tester", 15000000);
        NhanVien nv2 = new NhanVien("NV002", "Trần Thị B", 28, "Developer", 20000000);

        nv1.inThongTin();
        nv2.inThongTin();

        // Truy cập trực tiếp biến (cùng package, phạm vi default)
        System.out.println("Tên NV1: " + nv1.hoTen);
    }
}
