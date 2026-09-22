package com.kodewala;

import com.kodewala.DAO.CustomerDAO;
import com.kodewala.DAOImp.CustomerDAOImp;
import com.kodewala.Model.Customer;
import com.kodewala.Util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            CustomerDAO customerDAO = new CustomerDAOImp(connection);

            while (true) {

                System.out.println("\n===== Vehicle Rental System =====");
                System.out.println("1. Add Customer");
                System.out.println("2. Find Customer");
                System.out.println("3. View All Customers");
                System.out.println("4. Update Customer");
                System.out.println("5. Delete Customer");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1 -> {

                        Customer customer = new Customer();

                        System.out.print("Enter name: ");
                        customer.setName(scanner.nextLine());

                        System.out.print("Enter email: ");
                        customer.setEmail(scanner.nextLine());

                        System.out.print("Enter phone: ");
                        customer.setPhone(scanner.nextLine());

                        System.out.print("Enter address: ");
                        customer.setAddress(scanner.nextLine());

                        customerDAO.addCustomer(customer);

                        System.out.println(
                                "Customer added successfully."
                        );
                    }

                    case 2 -> {

                        System.out.print("Enter customer ID: ");
                        int id = scanner.nextInt();

                        Customer customer =
                                customerDAO.getCustomerByID(id);

                        if (customer != null) {

                            System.out.println(
                                    "ID: " + customer.getId()
                            );
                            System.out.println(
                                    "Name: " + customer.getName()
                            );
                            System.out.println(
                                    "Email: " + customer.getEmail()
                            );
                            System.out.println(
                                    "Phone: " + customer.getPhone()
                            );
                            System.out.println(
                                    "Address: " + customer.getAddress()
                            );

                        } else {

                            System.out.println(
                                    "Customer not found."
                            );
                        }
                    }

                    case 3 -> {

                        for (Customer customer : customerDAO.getAllCustomer()) {

                            System.out.println(
                                    customer.getId() + " | " +
                                            customer.getName() + " | " +
                                            customer.getEmail() + " | " +
                                            customer.getPhone() + " | " +
                                            customer.getAddress()
                            );
                        }
                    }

                    case 4 -> {

                        System.out.print("Enter customer ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Customer customer =
                                customerDAO.getCustomerByID(id);

                        if (customer == null) {

                            System.out.println(
                                    "Customer not found."
                            );
                            break;
                        }

                        System.out.print("Enter new name: ");
                        customer.setName(scanner.nextLine());

                        System.out.print("Enter new email: ");
                        customer.setEmail(scanner.nextLine());

                        System.out.print("Enter new phone: ");
                        customer.setPhone(scanner.nextLine());

                        System.out.print("Enter new address: ");
                        customer.setAddress(scanner.nextLine());

                        customerDAO.updateCustomer(customer);

                        System.out.println(
                                "Customer updated successfully."
                        );
                    }

                    case 5 -> {

                        System.out.print("Enter customer ID: ");
                        int id = scanner.nextInt();

                        customerDAO.deleteCustomerById(id);

                        System.out.println(
                                "Customer deleted successfully."
                        );
                    }

                    case 6 -> {

                        System.out.println(
                                "Thank you for using Vehicle Rental System."
                        );

                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database error: " + e.getMessage()
            );
        }
    }
}