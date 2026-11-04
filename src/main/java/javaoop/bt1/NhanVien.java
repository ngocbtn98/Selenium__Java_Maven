package javaoop.bt1;

/**
 * BT-JAVA OOP 1: Class NhanVien lưu thông tin nhân viên cơ bản.
 */
public class NhanVien {
    String maNV;
    String hoTen;
    int tuoi;
    String chucVu;
    double luong;

    public NhanVien(String maNV, String hoTen, int tuoi, String chucVu, double luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    public void inThongTin() {
        System.out.println("Mã NV: " + maNV);
        System.out.println("Họ tên: " + hoTen);
        System.out.println("Tuổi: " + tuoi);
        System.out.println("Chức vụ: " + chucVu);
        System.out.println("Lương: " + luong);
        System.out.println("-----------------------");
    }
}
