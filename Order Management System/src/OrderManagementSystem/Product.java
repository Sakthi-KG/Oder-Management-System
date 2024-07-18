package OrderManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Product {
    private Connection con;
    private Scanner sc;

    public Product(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addProduct() {
        try {
            System.out.print("Enter product id : ");
            int productId = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter product name : ");
            String productName = sc.nextLine();
            System.out.print("Enter product quantity : ");
            int productQuantity = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter delivery status : ");
            String deliveryStatus = sc.nextLine();

            String query = "INSERT INTO product (productId, productName, productQuantity, deliveryStatus) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            preparedStatement.setString(2, productName);
            preparedStatement.setInt(3, productQuantity);
            preparedStatement.setString(4, deliveryStatus);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Product details added successfully");
            } else {
                System.out.println("Sorry! Failed to add the details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewProducts() {
        try {
            String query = "SELECT * FROM product";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Products: ");
            System.out.println("+------------+----------------------+----------------+-----------------+");
            System.out.printf("| %-10s | %-20s | %-14s | %-15s |\n", "Product ID", "Product Name", "Product Quantity", "Delivery Status");
            System.out.println("+------------+----------------------+----------------+-----------------+");

            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int productQuantity = resultSet.getInt("productQuantity");
                String deliveryStatus = resultSet.getString("deliveryStatus");

                System.out.printf("| %-10s | %-20s | %-14s | %-15s |\n", productId, productName, productQuantity, deliveryStatus);
            }
            System.out.println("+------------+----------------------+----------------+-----------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getProductById(int productId) {
        String query = "SELECT * FROM product WHERE productId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                printProductDetails(resultSet);
                return true;
            } else {
                System.out.println("Product not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void printProductDetails(ResultSet resultSet) throws SQLException {
        System.out.println("Product found:");
        System.out.println("+------------+----------------------+----------------+-----------------+");
        System.out.printf("| %-10s | %-20s | %-14s | %-15s |\n", "Product ID", "Product Name", "Product Quantity", "Delivery Status");
        System.out.println("+------------+----------------------+----------------+-----------------+");

        int productId = resultSet.getInt("productId");
        String productName = resultSet.getString("productName");
        int productQuantity = resultSet.getInt("productQuantity");
        String deliveryStatus = resultSet.getString("deliveryStatus");

        System.out.printf("| %-10s | %-20s | %-14s | %-15s |\n", productId, productName, productQuantity, deliveryStatus);
        System.out.println("+------------+----------------------+----------------+-----------------+");
    }

    public void deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE productId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, productId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Product deleted successfully");
            } else {
                System.out.println("Product with ID " + productId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(int productId, int newQuantity) {
        String query = "UPDATE product SET productQuantity = ? WHERE productId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, productId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Product quantity updated successfully");
            } else {
                System.out.println("Product with ID " + productId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
