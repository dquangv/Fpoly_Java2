/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5.BaiTrenLop;

import java.io.Serializable;

/**
 *
 * @author Quang
 */
public class NhanVien implements Serializable {

    private String ma;
    private String ten;
    private String gioitinh;
    private String vaitro;

    public NhanVien() {
    }

    public NhanVien(String ma, String ten, String gioitinh, String vaitro) {
        this.ma = ma;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.vaitro = vaitro;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return this.ma + " - " + this.ten;
    }
}
