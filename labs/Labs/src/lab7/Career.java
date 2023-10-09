/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

import com.sun.tools.javac.Main;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quang
 */
public enum Career {
    UDPM, TKTW, LTDD, TKDH;
    
    public static class PolyStudent {
        public String fullname;
        public Career career;
        
        public void print() {
            System.out.println("Fullname: " + this.fullname);
            switch (this.career) {
                case UDPM:
                    System.out.println("Ung dung phan mem");
                    break;
                case TKTW:
                    System.out.println("Thiet ke trang web");
                    break;
                case LTDD:
                    System.out.println("Lap trinh di dong");
                    break;
                case TKDH:
                    System.out.println("Thiet ke do hoa");
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        PolyStudent sv = new PolyStudent();
        sv.fullname = "Vu Dang Quang";
        sv.career = Career.valueOf("UDPM");
        sv.print();
    }
}