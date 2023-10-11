/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nvcmis.sumary1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author volod
 */
public class XFile {
    public static void saveDataToFile(List<Product> list, String path){
        try{
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            fos.close();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static List<Product> readDataFromFile(String path){
        try{
            FileInputStream fi = new FileInputStream(path);
            ObjectInputStream oi = new ObjectInputStream(fi);
            return (List<Product>)oi.readObject();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
