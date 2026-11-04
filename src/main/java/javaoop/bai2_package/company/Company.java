package javaoop.bai2_package.company;

import javaoop.bai2_package.person.Person;

public class Company {
    public static void main(String[] args) {
        Person person = new Person("Nguyễn Văn A", 25, "Nam", "Hà Nội", "0987654321");
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Gender: " + person.getGender());
        // person.getAddress(); // Lỗi compile: getAddress() không public => không gọi được từ package khác
        // person.getPhone();   // Lỗi compile: getPhone() không public => không gọi được từ package khác
    }
}
