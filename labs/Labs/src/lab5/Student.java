/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quang
 */
public class Student implements Serializable {

    public String name;
    public double marks;
    public String major;

    public Student(String name, double marks, String major) {
        this.name = name;
        this.marks = marks;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        if (this.marks < 3) {
            return "Kém";
        } else if (this.marks < 5) {
            return "Yếu";
        } else if (this.marks < 6.5) {
            return "Trung bình";
        } else if (this.marks < 7.5) {
            return "Khá";
        } else if (this.marks < 9) {
            return "Giỏi";
        }

        return "Xuất sắc";
    }

    public boolean isBonus() {
        return this.marks >= 7.5;
    }

    public static void main(String[] args) throws RuntimeException, IOException, FileNotFoundException, ClassNotFoundException {
        List<Student> list = new ArrayList<>();

        list.add(new Student("Quang", 9.7, "PTPM"));
        list.add(new Student("Long", 7, "Marketing"));
        list.add(new Student("Ngọc", 10, "NNA"));

        XFile.writeObject("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab5\\lab5Bai2.dat", list);

        List<Student> list2 = (List<Student>) XFile.readObject("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab5\\lab5Bai2.dat");

        for (Student student : list2) {
            System.out.println("Tên: " + student.getName());
            System.out.println("Điểm: " + student.getMarks());
            System.out.println("Ngành: " + student.getMajor());
            System.out.println("Học lực: " + student.getGrade());
            System.out.println("Nhận thưởng: " + (student.isBonus() ? "Có" : "Không"));
        }
    }
}
