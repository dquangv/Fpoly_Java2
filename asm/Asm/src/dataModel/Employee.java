/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataModel;

import MyException.MyException;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author Quang
 */
public class Employee implements Serializable {

    private String code, name, email;
    private int age;
    private double salary;

    public Employee() {
    }

    public Employee(String code, String name, int age, String email, double salary) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.age = age;
        this.salary = salary;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws MyException {
        // \\p{L} cho phép nhận ký tự có dấu
        String nameRegEx = "[a-zA-Z\\p{L}]+([\\s+a-zA-Z\\p{L}])*";

        if (name.matches(nameRegEx)) {
            this.name = name;
        } else {
            throw new MyException("Name only contains word characters", "name");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws MyException {
        String emailRegEx = "\\w+@\\w+\\.\\w+";

        if (email.matches(emailRegEx)) {
            this.email = email;
        } else {
            throw new MyException("Email is not valid", "mail");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws MyException {
        if (age < 16 || age > 55) {
            throw new MyException("Not of working age", "age");
        } else {
            this.age = age;
        }
    }

    public double getSalary() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(salary));
    }

    public void setSalary(double salary) throws MyException {
        if (salary < 5000) {
            throw new MyException("Salary must be greater than 0", "salary");
        } else {
            this.salary = salary;
        }
    }

}
