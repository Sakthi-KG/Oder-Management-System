package OrderManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Order {
    private Connection con;
    private Scanner sc;

    public Order(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addOrder() {
        try {
            System.out.print("Enter order id : ");
            int orderId = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter order quantity : ");
            int orderQuantity = sc.nextInt();
            sc.nextLine(); // Consume newline left-over

            String query = "INSERT INTO orders (orderId, orderQuantity) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, orderQuantity);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Order details added successfully");
            } else {
                System.out.println("Sorry! Failed to add the details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewOrders() {
        try {
            String query = "SELECT * FROM orders";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Orders: ");
            System.out.println("+------------+-----------------+");
            System.out.printf("| %-10s | %-15s |\n", "Order ID", "Order Quantity");
            System.out.println("+------------+-----------------+");

            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                int orderQuantity = resultSet.getInt("orderQuantity");

                System.out.printf("| %-10s | %-15s |\n", orderId, orderQuantity);
            }
            System.out.println("+------------+-----------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getOrderById(int orderId) {
        String query = "SELECT * FROM orders WHERE orderId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                printOrderDetails(resultSet);
                return true;
            } else {
                System.out.println("Order not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void printOrderDetails(ResultSet resultSet) throws SQLException {
        System.out.println("Order found:");
        System.out.println("+------------+-----------------+");
        System.out.printf("| %-10s | %-15s |\n", "Order ID", "Order Quantity");
        System.out.println("+------------+-----------------+");

        int orderId = resultSet.getInt("orderId");
        int orderQuantity = resultSet.getInt("orderQuantity");

        System.out.printf("| %-10s | %-15s |\n", orderId, orderQuantity);
        System.out.println("+------------+-----------------+");
    }
}

