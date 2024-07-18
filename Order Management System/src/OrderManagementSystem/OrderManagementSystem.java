package OrderManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class OrderManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/myDB";
    private static final String username = "root";
    private static final String password = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner scanner = new Scanner(System.in);
            Order order = new Order(connection, scanner);
            Product product = new Product(connection, scanner);
            Customer customer = new Customer(connection, scanner);

            while (true) {
                System.out.println("ORDER MANAGEMENT SYSTEM");
                System.out.println("1. Add Order");
                System.out.println("2. View Orders");
                System.out.println("3. Add Product");
                System.out.println("4. View Products");
                System.out.println("5. Add Customer");
                System.out.println("6. View Customers");
                System.out.println("7. Delete Product");
                System.out.println("8. Delete Customer");
                System.out.println("9. Update Product Quantity");
                System.out.println("10. Update Customer Phone");
                System.out.println("11. Update Customer Email");
                System.out.println("12. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        order.addOrder();
                        break;
                    case 2:
                        order.viewOrders();
                        break;
                    case 3:
                        product.addProduct();
                        break;
                    case 4:
                        product.viewProducts();
                        break;
                    case 5:
                        customer.addCustomer();
                        break;
                    case 6:
                        customer.viewCustomer();
                        break;
                    case 7:
                        System.out.print("Enter Product ID to delete: ");
                        int productIdToDelete = scanner.nextInt();
                        product.deleteProduct(productIdToDelete);
                        break;
                    case 8:
                        System.out.print("Enter Customer ID to delete: ");
                        int customerIdToDelete = scanner.nextInt();
                        customer.deleteCustomer(customerIdToDelete);
                        break;
                    case 9:
                        System.out.print("Enter Product ID to update quantity: ");
                        int productIdToUpdate = scanner.nextInt();
                        System.out.print("Enter new quantity: ");
                        int newQuantity = scanner.nextInt();
                        product.updateProductQuantity(productIdToUpdate, newQuantity);
                        break;
                    case 10:
                        System.out.print("Enter Customer ID to update phone: ");
                        int customerIdToUpdate = scanner.nextInt();
                        System.out.print("Enter new phone number: ");
                        scanner.nextLine();
                        String newPhone = scanner.nextLine();
                        customer.updateCustomerPhone(customerIdToUpdate, newPhone);
                        break;
                    case 11:
                        System.out.print("Enter Customer ID to update email: ");
                        int customerIdToUpdateEmail = scanner.nextInt();
                        System.out.print("Enter new email: ");
                        scanner.nextLine();
                        String newEmail = scanner.nextLine();
                        customer.updateCustomerEmail(customerIdToUpdateEmail, newEmail);
                        break;
                    case 12:
                        System.out.println("Exiting...Thank you!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
