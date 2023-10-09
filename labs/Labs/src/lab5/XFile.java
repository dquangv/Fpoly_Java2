/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Quang
 */
public class XFile {

    public static byte[] read(String path) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(path);
        int length = fis.available();
        byte[] data = new byte[length];
        
        fis.read(data);
        fis.close();
        return data;
    }
    
    public static void write(String path, byte[] data) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(path);
        
        fos.write(data);
        fos.close();
    }
    
    public static Object readObject(String path) throws RuntimeException, FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
    
    public static void writeObject(String path, Object object)throws RuntimeException, FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        
        oos.writeObject(object);
        oos.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = "C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab5\\lab5Bai1.dat";
        byte[] data = "Hello, Quang".getBytes();
        
        write(path, data);
        
        byte[] dataCopy = read(path);
        String pathCopy = "C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab5\\lab5Bai1Copy.dat";
        
        write(pathCopy, ("Dữ liệu được copy từ file" + "\n" + path + "\n" + new String(dataCopy)).getBytes());
    }
}
