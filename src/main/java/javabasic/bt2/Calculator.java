package javabasic.bt2;

/**
 * BT-JAVA BASIC 2: Method và Data type.
 */
public class Calculator {

    // Hàm tính tổng 2 số nguyên
    public static int tinhTong(int a, int b) {
        return a + b;
    }

    // Hàm tính tích 2 số thực
    public static double tinhTich(double a, double b) {
        return a * b;
    }

    public static void main(String[] args) {
        int tong = tinhTong(5, 7);
        double tich = tinhTich(2.5, 4.2);

        System.out.println("Tổng 5 + 7 = " + tong);
        System.out.println("Tích 2.5 * 4.2 = " + tich);
    }
}
