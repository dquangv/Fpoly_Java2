package lab1;

import java.util.Scanner;

public class Output {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Amount of products: ");
        Integer amountPro = Integer.parseInt(sc.nextLine());
        
        Product[] products = new Product[amountPro];
        
        for (int i = 0; i < products.length; i++) {
            System.out.println("Product " + (i + 1));
            System.out.println("Name: ");
            String name = sc.nextLine();
            
            System.out.println("Price: ");
            double price = Double.parseDouble(sc.nextLine());
            
            products[i] = new Product();
            products[i].setName(name);
            products[i].setPrice(price);
        }
        
        for (int i = 0; i < products.length; i++) {
            System.out.println("Product " + (i + 1));
            System.out.println("Name: " + products[i].getName());
            System.out.println("Price: " + products[i].getPrice());
            System.out.println("Tax: " + products[i].getImportTax());
        }
    }
}
