/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyException;

/**
 *
 * @author Quang
 */
public class MyException extends Exception {
    public String error;
    public String CodeError;

    public MyException(String error, String CodeError) {
        this.error = error;
        this.CodeError = CodeError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCodeError() {
        return CodeError;
    }

    public void setCodeError(String CodeError) {
        this.CodeError = CodeError;
    }
    
}
