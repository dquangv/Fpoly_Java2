package lab4;

import java.util.Date;
import lab4.MyException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Quang
 */
public class Staff {

    private String name;

    public String getName() {
        return name;
    }
    private double salary;
    private int age;
    private Date birthday;

    public Staff() {
    }

    public Staff(String name, double salary, int age, Date birthday) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) throws MyException {
        if (salary < 0) {
            throw new MyException("Salary must be greater than 0", "02");
        } else if (salary > 1000000000) {
            throw new MyException("Your input seems suspicious", "03");
        } else {
            this.salary = salary;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws MyException {
        if (age > 0 && age < 120) {
            this.age = age;
        } else {
            throw new MyException("Age must be between 0 and 120", "04");
        }
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) throws RuntimeException {
        this.birthday = birthday;
    }

}
