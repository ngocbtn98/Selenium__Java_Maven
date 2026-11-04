package javaoop.bai1_objectclass;

public class ThongTin {
    public static void main(String[] args) {
        NhanVien nv1 = new NhanVien("Nguyễn Văn A", 25, "Tester", 15000000);
        NhanVien nv2 = new NhanVien("Trần Thị B", 28, "Developer", 20000000);

        System.out.println("===== Nhân viên 1 =====");
        nv1.hienThiThongTin();
        System.out.println("===== Nhân viên 2 =====");
        nv2.hienThiThongTin();
        System.out.println("Gọi trực tiếp thuộc tính: " + nv1.hoTen + " - " + nv1.tuoi);
    }
}
