package javabasic.bt5;

import java.util.ArrayList;

/**
 * BT-JAVA BASIC 5 (phần 1): Xử lý lại BT Java Basic 4 bằng ArrayList.
 */
public class SoChanArrayList {
    public static void main(String[] args) {
        ArrayList<Integer> listSoChan = new ArrayList<>();

        for (int i = 0; i <= 50; i += 2) {
            listSoChan.add(i);
        }

        System.out.println("Số lượng số chẵn: " + listSoChan.size());
        for (int i = 0; i < listSoChan.size(); i++) {
            System.out.println("listSoChan[" + i + "] = " + listSoChan.get(i));
        }
    }
}
