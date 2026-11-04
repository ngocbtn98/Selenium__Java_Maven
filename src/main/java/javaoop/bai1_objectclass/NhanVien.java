package javaoop.bai1_objectclass;

/**
 * BT-JAVA OOP 1: Object - Class.
 */
public class NhanVien {
    String hoTen;
    int tuoi;
    String chucVu;
    double luong;

    public NhanVien(String hoTen, int tuoi, String chucVu, double luong) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    public void hienThiThongTin() {
        System.out.println("Họ tên: " + hoTen);
        System.out.println("Tuổi: " + tuoi);
        System.out.println("Chức vụ: " + chucVu);
        System.out.println("Lương: " + luong);
    }
}
