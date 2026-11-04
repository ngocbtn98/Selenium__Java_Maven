package javabasic.bai3_operators;

/**
 * BT-JAVA BASIC 3: Toán tử quan hệ, logic và câu lệnh IF ELSE.
 */
public class ToanTu {
    int a = 10;
    int b = 20;
    int c = 10;

    // Biến cố định
    static final int NUMBER = 100;

    public void toanTuQuanHe() {
        System.out.println("===== Toán tử quan hệ =====");
        System.out.println("a > b: " + (a > b));
        System.out.println("a < b: " + (a < b));
        System.out.println("a == c: " + (a == c));
    }

    public void toanTuLogic() {
        System.out.println("===== Toán tử logic =====");
        System.out.println("(a < b) && (a == c): " + ((a < b) && (a == c)));
        System.out.println("(a > b) && (a == c): " + ((a > b) && (a == c)));
        System.out.println("(a > b) || (a == c): " + ((a > b) || (a == c)));
        System.out.println("(a > b) || (b < c): " + ((a > b) || (b < c)));
    }

    public void soSanh(int n) {
        if (n == NUMBER) {
            System.out.println(n + " bằng " + NUMBER);
        } else if (n < NUMBER) {
            System.out.println(n + " nhỏ hơn " + NUMBER);
        } else {
            System.out.println(n + " lớn hơn " + NUMBER);
        }
    }

    public static void main(String[] args) {
        ToanTu toanTu = new ToanTu();
        toanTu.toanTuQuanHe();
        toanTu.toanTuLogic();

        System.out.println("===== IF ELSE =====");
        toanTu.soSanh(100);
        toanTu.soSanh(50);
        toanTu.soSanh(150);
    }
}
