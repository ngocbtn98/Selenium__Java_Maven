package javabasic.bai4_for_array;

/**
 * BT-JAVA BASIC 4: Vòng lặp FOR và mảng.
 */
public class SoChan {
    public static void main(String[] args) {
        // Từ 0 đến 50 có 26 số chẵn
        int[] mangSoChan = new int[26];
        int index = 0;

        System.out.println("Các số chẵn từ 0 đến 50:");
        for (int i = 0; i <= 50; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
                mangSoChan[index] = i;
                index++;
            }
        }
        System.out.println();

        System.out.println("Duyệt mảng sau khi nạp:");
        for (int i = 0; i < mangSoChan.length; i++) {
            System.out.println("mangSoChan[" + i + "] = " + mangSoChan[i]);
        }
    }
}
