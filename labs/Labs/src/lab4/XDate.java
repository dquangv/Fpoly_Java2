/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab4;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Quang
 */
public class XDate {
    private static SimpleDateFormat formatter = new SimpleDateFormat();
    
    public static Date parse(String text, String pattern) throws RuntimeException {
        try {
            formatter.applyPattern(pattern);
            return formatter.parse(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Date parse(String text) throws RuntimeException {
        return XDate.parse(text, "dd-MM-yyyy");
    }
    
}
