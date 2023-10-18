/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BayTT;

/**
 *
 * @author Quang
 */
public class Staff {
    String codeStaff;
    String nameStaff;
    String phoneStaff;

    public Staff() {
    }

    public Staff(String codeStaff, String nameStaff, String phoneStaff) {
        this.codeStaff = codeStaff;
        this.nameStaff = nameStaff;
        this.phoneStaff = phoneStaff;
    }

    public String getCodeStaff() {
        return codeStaff;
    }

    public void setCodeStaff(String codeStaff) {
        this.codeStaff = codeStaff;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public String getPhoneStaff() {
        return phoneStaff;
    }

    public void setPhoneStaff(String phoneStaff) {
        this.phoneStaff = phoneStaff;
    }
    
    
}
