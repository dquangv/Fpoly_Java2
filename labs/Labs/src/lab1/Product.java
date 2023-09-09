package lab1;

public class Product implements DAO {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product() {
    }
    
    public double getImportTax() {
        return this.price * 0.1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void insert() {
        System.out.println("Insert successfully!");
    }

    @Override
    public void update() {
        System.out.println("Update successfully!");
    }

    @Override
    public void delete() {
        System.out.println("Delete successfully!");
    }

    @Override
    public void select() {
        System.out.println("Select successfully!");
    }
}
