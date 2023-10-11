
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Quang
 */
public class Bai2 {
    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        
        for (int i = 0; i < 10; i++) {
            arr.add(i+1);
        }
        
        for (Integer i : arr) {
            System.out.println(i);
        }
    }
}
