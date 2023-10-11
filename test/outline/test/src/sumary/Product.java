/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nvcmis.sumary1;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author volod
 */
public class Product implements Serializable {
    public int id;
    public String name;
    public String manufacturer;
    public Date expireDate;

    public Product(int id, String name, String manufacturer, Date expireDate) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expireDate = expireDate;
    }
    
}
