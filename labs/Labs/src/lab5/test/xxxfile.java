/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab5.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Quang
 */
public class xxxfile {
    public byte[] read(String path) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = new byte[fis.available()];
        
        fis.read(data);
        fis.close();
        return data;
    }
    
    public void write(String path, byte[] data) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(path);
        
        fos.write(data);
        fos.close();
    }
    
    public Object readObj(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
    
    public void writeObj(String path, Object obj) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        
        oos.writeObject(obj);
        oos.close();
    }
    
    public String readFile(String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        String line = br.readLine();
        String content = null;
        while (line != null) {
            content += line;
            line = br.readLine();
        }
        
        br.close();
        return content;
    }
    
    public void writeFile(String path, String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(data);
        bw.newLine();
        bw.close();
    }
}
