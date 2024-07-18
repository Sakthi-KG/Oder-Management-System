
package OrderManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customer {
    private Connection con;
    private Scanner sc;

    public Customer(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addCustomer() {
        try {
            System.out.print("Enter customer id : ");
            int customerId = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            System.out.print("Enter customer name : ");
            String customerName = sc.nextLine();
            System.out.print("Enter customer address : ");
            String customerAddress = sc.nextLine();
            System.out.print("Enter customer phone : ");
            String customerPhone = sc.nextLine();

            String query = "INSERT INTO customer (customerId, customerName, customerAddress, customerPhone) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.setString(2, customerName);
            preparedStatement.setString(3, customerAddress);
            preparedStatement.setString(4, customerPhone);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer details added successfully");
            } else {
                System.out.println("Sorry! Failed to add the details");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewCustomer() {
        try {
            String query = "SELECT * FROM customer";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Customers: ");
            System.out.println("+--------------+----------------------+----------------------+----------------------+");
            System.out.printf("| %-12s | %-20s | %-20s | %-20s |\n", "Customer ID", "Customer Name", "Customer Address", "Customer Phone");
            System.out.println("+--------------+----------------------+----------------------+----------------------+");

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                String customerName = resultSet.getString("customerName");
                String customerAddress = resultSet.getString("customerAddress");
                String customerPhone = resultSet.getString("customerPhone");

                System.out.printf("| %-12s | %-20s | %-20s | %-20s |\n", customerId, customerName, customerAddress, customerPhone);
            }
            System.out.println("+--------------+----------------------+----------------------+----------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getCustomerById(int customerId) {
        String query = "SELECT * FROM customer WHERE customerId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                printCustomerDetails(resultSet);
                return true;
            } else {
                System.out.println("Customer not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void printCustomerDetails(ResultSet resultSet) throws SQLException {
        System.out.println("Customer found:");
        System.out.println("+--------------+----------------------+----------------------+----------------------+");
        System.out.printf("| %-12s | %-20s | %-20s | %-20s |\n", "Customer ID", "Customer Name", "Customer Address", "Customer Phone");
        System.out.println("+--------------+----------------------+----------------------+----------------------+");

        int customerId = resultSet.getInt("customerId");
        String customerName = resultSet.getString("customerName");
        String customerAddress = resultSet.getString("customerAddress");
        String customerPhone = resultSet.getString("customerPhone");

        System.out.printf("| %-12s | %-20s | %-20s | %-20s |\n", customerId, customerName, customerAddress, customerPhone);
        System.out.println("+--------------+----------------------+----------------------+----------------------+");
    }

    public void deleteCustomer(int customerId) {
        String query = "DELETE FROM customer WHERE customerId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, customerId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer deleted successfully");
            } else {
                System.out.println("Customer with ID " + customerId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCustomerEmail(int customerId, String newEmail) {
        String query = "UPDATE customer SET customerEmail = ? WHERE customerId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, customerId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer email updated successfully");
            } else {
                System.out.println("Customer with ID " + customerId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCustomerPhone(int customerId, String newPhone) {
        String query = "UPDATE customer SET customerPhone = ? WHERE customerId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newPhone);
            preparedStatement.setInt(2, customerId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer phone updated successfully");
            } else {
                System.out.println("Customer with ID " + customerId + " not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

