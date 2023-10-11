/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8;

/**
 *
 * @author Quang
 */
public class ProductManager {
    public static void main(String[] args) {
        Product pro1 = new Product("Iphone XS Max", Double.parseDouble("8000000"));
        Product pro2 = new Product("Samsung Galaxy A52", Double.parseDouble("10000000"));
        
        ProductDAO dao = new ProductDAO();
        dao.add(pro1);
        dao.add(pro2);
        
        dao.store("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab8\\product.dat");
        
        ProductDAO dao2 = new ProductDAO(); 
        dao2.load("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\labs\\Labs\\src\\lab8\\product.dat");
        
        Product pro = dao2.find("Iphone XS Max");
        System.out.println(pro.getName());
        System.out.println(pro.getPrice());
    }
}
