/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import MyException.MyException;

/**
 *
 * @author Quang
 */
public interface IEmpManagement {
    void Open();
    void Exit();
    void New();
    void Save() throws MyException;
    void Delete();
    void Find();
    void First();
    void Pre();
    void Next();
    void Last();
}
