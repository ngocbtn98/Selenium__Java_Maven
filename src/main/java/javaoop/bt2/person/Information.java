package javaoop.bt2.person;

/**
 * BT-JAVA OOP 2: Class Information (cùng package person) gọi lại TẤT CẢ thông tin của Person.
 */
public class Information {
    public static void main(String[] args) {
        Person person = new Person("Nguyễn Văn A", 25, "Nam", "Hà Nội", "0901234567");

        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Gender: " + person.getGender());
        System.out.println("Address: " + person.getAddress());
        System.out.println("Phone: " + person.getPhone());
    }
}
