package javaoop.bt2.company;

import javaoop.bt2.person.Person;

/**
 * BT-JAVA OOP 2: Class Company (khác package) chỉ gọi được name, age, gender.
 * getAddress() và getPhone() có phạm vi default nên KHÔNG gọi được từ đây (sẽ lỗi compile).
 */
public class Company {
    public static void main(String[] args) {
        Person person = new Person("Trần Thị B", 30, "Nữ", "TP.HCM", "0987654321");

        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Gender: " + person.getGender());

        // person.getAddress(); // Lỗi compile: getAddress() is not public in Person
        // person.getPhone();   // Lỗi compile: getPhone() is not public in Person
    }
}
