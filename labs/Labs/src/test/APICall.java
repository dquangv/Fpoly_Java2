/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author Quang
 */
public class APICall implements IAPICall {
    
    public Object[][] listToObjectArray(List<Staff> list) {
        Object[][] array = new Object[list.size()][4];
        
        for (int i = 0; i < list.size(); i++) {
            Staff staff = new Staff();
            array[i][0] = staff.getMaTruong();
            array[i][1] = staff.getTenTruong();
            array[i][2] = staff.getDiaChi();
            array[i][3] = staff.getThongTin();
        }
        
        return array;
    }
    
    @Override
    public String getUniFromApi() {
        try {
            URL url = new URL("https://internship-api.dev.altasoftware.vn/api/university");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            
            int responeCode = con.getResponseCode();
            
            if (responeCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                
                return response.toString();
            }
        } catch (Exception e) {
        }
        return "No data";
    }

    @Override
    public Object[][] getuniList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
