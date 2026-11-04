package javabasic.bai2_method;

/**
 * BT-JAVA BASIC 2: Method và Data type.
 */
public class Calculator {

    // Hàm tính tổng 2 số nguyên
    public int tinhTong(int a, int b) {
        return a + b;
    }

    // Hàm tính tích 2 số thực
    public double tinhTich(double a, double b) {
        return a * b;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println("Tổng 5 + 7 = " + calculator.tinhTong(5, 7));
        System.out.println("Tích 2.5 * 4.2 = " + calculator.tinhTich(2.5, 4.2));
    }
}
