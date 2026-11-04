package javaoop.bt2.person;

/**
 * BT-JAVA OOP 2: Package và phạm vi truy cập.
 * - name, age, gender: public getter -> package khác (company) gọi được
 * - address, phone: getter phạm vi default (package-private) -> chỉ package "person" gọi được
 */
public class Person {
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phone;

    public Person(String name, int age, String gender, String address, String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    // Không có "public" => chỉ các class trong package person truy cập được
    String getAddress() {
        return address;
    }

    String getPhone() {
        return phone;
    }
}
