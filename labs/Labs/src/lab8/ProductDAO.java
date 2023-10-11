/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8;

import java.io.Serializable;

/**
 *
 * @author Quang
 */
public class ProductDAO extends DAO<Product> {

    @Override
    public void update(Product ent) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(ent.getName())) {
                list.set(i, ent);
            }
        }
    }

    @Override
    public Product find(Serializable id) {
        for (Product pro : list) {
            if (pro.getName().equals(id)) {
                return pro;
            }
        }
        
        return null;
    }
}
