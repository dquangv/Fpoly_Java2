package lab1;

public class NoTaxProduct extends Product {
    
    public NoTaxProduct(String name, double price) {
        super(name, price);
    }
    
    public NoTaxProduct() {
    }
    
    @Override
    public double getImportTax() {
        return 0;
    }
}
