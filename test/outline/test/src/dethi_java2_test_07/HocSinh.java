/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dethi_java2_test_07;

import java.io.Serializable;

/**
 *
 * @author Quang
 */
public class HocSinh implements Serializable {
    String HoTen;
    double Toan, Ly, Hoa;

    public HocSinh(String HoTen, double Toan, double Ly, double Hoa) {
        this.HoTen = HoTen;
        this.Toan = Toan;
        this.Ly = Ly;
        this.Hoa = Hoa;
    }
    
    public double diemTrungBinh(double Toan, double Ly, double Hoa) {
        return (Toan + Ly + Hoa) / 3;
    }
    
    public String ketQua(double diemTB) {
        if (diemTB >= 5) {
            return "Đạt";
        }
        
        return "Không đạt";
    }
}
