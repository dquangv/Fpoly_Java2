/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lab3.dataModel.Student;

/**
 *
 * @author Quang
 */
public class MapDemo {

    public static void main(String[] args) {
        Map<String, Student> map = new HashMap<String, Student>();
        Student sv1 = new Student("Ngọc", 10, "Thiết kế đồ hoạ");
        Student sv2 = new Student("Tuyết", 8, "Phát triển phần mềm");
        Student sv3 = new Student("Vinh", 5, "Ứng dụng phần mềm");

        map.put(sv1.getName(), sv1);
        map.put(sv2.getName(), sv2);
        map.put(sv3.getName(), sv3);

        Set<String> keys = map.keySet();
        System.out.println(keys);

        for (String key : keys) {
            Student sv = map.get(key);
            System.out.println(">Họ và tên: " + sv.getName());
            System.out.println(">Học lực: " + sv.getAcademic());
        }
    }
}
