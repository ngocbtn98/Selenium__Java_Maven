package javabasic.bt3;

/**
 * BT-JAVA BASIC 3: Toán tử quan hệ, logic và câu lệnh if else.
 */
public class ToanTu {
    // Biến cố định
    static final int number = 100;

    // 3 biến bất kỳ
    static int a = 10;
    static int b = 20;
    static int c = 10;

    public static void kiemTraToanTu() {
        // Toán tử quan hệ
        System.out.println("a > b: " + (a > b));
        System.out.println("a < b: " + (a < b));
        System.out.println("a == c: " + (a == c));

        // Toán tử logic
        System.out.println("(a < b) && (a == c): " + ((a < b) && (a == c)));
        System.out.println("(a > b) || (a == c): " + ((a > b) || (a == c)));
        System.out.println("(a > b) && (b > c): " + ((a > b) && (b > c)));
    }

    public static void soSanh(int n) {
        if (n == number) {
            System.out.println(n + " bằng " + number);
        } else if (n < number) {
            System.out.println(n + " nhỏ hơn " + number);
        } else {
            System.out.println(n + " lớn hơn " + number);
        }
    }

    public static void main(String[] args) {
        kiemTraToanTu();

        soSanh(100); // TH1
        soSanh(50);  // TH2
        soSanh(150); // TH3
    }
}
