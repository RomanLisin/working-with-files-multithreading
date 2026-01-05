package ru.packajes.files;

public class Employee {
    private int id;
    private String lastName;
    private String firstName;
    private int age;
    private String position;
    private String department;

    public Employee(int id, String lastName, String firstName, int age, String position, String department) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.position = position;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "%d: %s %s, %d лет, %s, отдел: %s".formatted(id, lastName, firstName, age, position, department);
    }

    public String toFileString() {
        return "%d|%s|%s|%d|%s|%s".formatted(id, lastName, firstName, age, position, department);
    }
    public static Employee fromFileString(String line){
        String[] parts = line.split("\\|");
        return new Employee(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                parts[4],
                parts[5]
        );
    }
}


