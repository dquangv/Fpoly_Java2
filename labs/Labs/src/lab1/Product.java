package lab1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try (Connection conn = OutputDAO.getConnection()) {
            String sql = "insert into product (name, price) values (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, this.name);
                statement.setDouble(2, this.price);
                statement.executeUpdate();
                System.out.println("Insert successfully!");
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void update() {
        try (Connection conn = OutputDAO.getConnection()) {
            String sql = "update product set price = ? where name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setDouble(1, this.price);
                statement.setString(2, this.name);
                statement.executeUpdate();
                System.out.println("Update successfully!");
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void delete() {
        try (Connection conn = OutputDAO.getConnection()) {
            String sql = "delete from product where name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, this.name);
                statement.executeUpdate();
                System.out.println("Delete successfully!");
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public void select() {
        try (Connection conn = OutputDAO.getConnection()) {
            String sql = "select name, price from product where name = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, this.name);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String productName = rs.getString("Name");
                    double productPrice = rs.getDouble("Price");
                    System.out.println("Name: " + productName);
                    System.out.println("Price: " + productPrice);
                }
                System.out.println("Select successfully!");
            }
        } catch (SQLException e) {
        }
    }

}
