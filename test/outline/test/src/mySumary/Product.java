/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mySumary;

import java.io.Serializable;
import javax.swing.Icon;

/**
 *
 * @author Quang
 */
public class Product implements Serializable {
    public String name;
    public int price;
    public Icon photo;

    public Product() {
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Icon getPhoto() {
        return photo;
    }

    public void setPhoto(Icon photo) {
        this.photo = photo;
    }

    public Product(String name, int price, Icon photo) {
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
