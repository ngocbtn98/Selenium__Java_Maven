package javaoop.bai2_package.person;

public class Information {
    public static void main(String[] args) {
        Person person = new Person("Nguyễn Văn A", 25, "Nam", "Hà Nội", "0987654321");
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Gender: " + person.getGender());
        System.out.println("Address: " + person.getAddress());
        System.out.println("Phone: " + person.getPhone());
    }
}
