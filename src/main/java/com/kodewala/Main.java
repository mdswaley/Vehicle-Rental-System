package com.kodewala;

import com.kodewala.DAO.CustomerDAO;
import com.kodewala.DAO.RentalDAO;
import com.kodewala.DAO.VehicleDAO;
import com.kodewala.DAOImp.CustomerDAOImp;
import com.kodewala.DAOImp.RentalDAOImp;
import com.kodewala.DAOImp.VehicleDAOImp;
import com.kodewala.Model.Constant.RentalStatus;
import com.kodewala.Model.Constant.VehicleStatus;
import com.kodewala.Model.Customer;
import com.kodewala.Model.Rental;
import com.kodewala.Model.Vehicle;
import com.kodewala.Util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            CustomerDAO customerDAO = new CustomerDAOImp(connection);

            VehicleDAO vehicleDAO = new VehicleDAOImp(connection);

            RentalDAO rentalDAO = new RentalDAOImp(connection);

            while (true) {

                System.out.println();
                System.out.println("================================");
                System.out.println("     VEHICLE RENTAL SYSTEM");
                System.out.println("================================");

                System.out.println("1.  Add Customer");
                System.out.println("2.  Find Customer");
                System.out.println("3.  View All Customers");
                System.out.println("4.  Update Customer");
                System.out.println("5.  Delete Customer");

                System.out.println("6.  Add Vehicle");
                System.out.println("7.  Find Vehicle");
                System.out.println("8.  View All Vehicles");
                System.out.println("9.  View Available Vehicles");
                System.out.println("10. Update Vehicle");
                System.out.println("11. Delete Vehicle");

                System.out.println("12. Create Rental");
                System.out.println("13. Find Rental");
                System.out.println("14. View All Rentals");
                System.out.println("15. View Customer Rentals");
                System.out.println("16. View Active Rentals");
                System.out.println("17. Return Vehicle");
                System.out.println("18. Cancel Rental");

                System.out.println("19. Exit");

                System.out.print("\nEnter choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                try {

                    switch (choice) {

                        // =========================
                        // CUSTOMER
                        // =========================

                        case 1 -> {

                            Customer customer =
                                    new Customer();

                            System.out.print(
                                    "Enter name: "
                            );
                            customer.setName(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter email: "
                            );
                            customer.setEmail(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter phone: "
                            );
                            customer.setPhone(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter address: "
                            );
                            customer.setAddress(
                                    scanner.nextLine()
                            );

                            customerDAO.addCustomer(
                                    customer
                            );

                            System.out.println(
                                    "Customer added successfully."
                            );
                        }

                        case 2 -> {

                            System.out.print(
                                    "Enter customer ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Customer customer =
                                    customerDAO
                                            .getCustomerByID(id);

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found."
                                );

                            } else {

                                printCustomer(customer);
                            }
                        }

                        case 3 -> {

                            List<Customer> customers =
                                    customerDAO
                                            .getAllCustomer();

                            if (customers.isEmpty()) {

                                System.out.println(
                                        "No customers found."
                                );

                            } else {

                                for (Customer customer :
                                        customers) {

                                    printCustomer(customer);
                                }
                            }
                        }

                        case 4 -> {

                            System.out.print(
                                    "Enter customer ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Customer customer =
                                    customerDAO
                                            .getCustomerByID(id);

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found."
                                );

                                break;
                            }

                            System.out.print(
                                    "Enter new name: "
                            );
                            customer.setName(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter new email: "
                            );
                            customer.setEmail(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter new phone: "
                            );
                            customer.setPhone(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Enter new address: "
                            );
                            customer.setAddress(
                                    scanner.nextLine()
                            );

                            customerDAO.updateCustomer(
                                    customer
                            );

                            System.out.println(
                                    "Customer updated successfully."
                            );
                        }

                        case 5 -> {

                            System.out.print(
                                    "Enter customer ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            customerDAO.deleteCustomerById(
                                    id
                            );

                            System.out.println(
                                    "Customer deleted successfully."
                            );
                        }

                        // =========================
                        // VEHICLE
                        // =========================

                        case 6 -> {

                            Vehicle vehicle =
                                    new Vehicle();

                            System.out.print(
                                    "Vehicle number: "
                            );
                            vehicle.setVehicleNumber(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Brand: "
                            );
                            vehicle.setBrand(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Model: "
                            );
                            vehicle.setModel(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Vehicle type: "
                            );
                            vehicle.setVehicleType(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Price per day: "
                            );

                            double price =
                                    scanner.nextDouble();

                            scanner.nextLine();

                            vehicle.setPricePerDay(
                                    price
                            );

                            vehicle.setStatus(
                                    VehicleStatus.AVAILABLE
                            );

                            vehicleDAO.addVehicle(
                                    vehicle
                            );

                            System.out.println(
                                    "Vehicle added successfully."
                            );
                        }

                        case 7 -> {

                            System.out.print(
                                    "Enter vehicle ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Vehicle vehicle =
                                    vehicleDAO
                                            .getVehicleById(id);

                            if (vehicle == null) {

                                System.out.println(
                                        "Vehicle not found."
                                );

                            } else {

                                printVehicle(vehicle);
                            }
                        }

                        case 8 -> {

                            List<Vehicle> vehicles =
                                    vehicleDAO
                                            .getAllVehicles();

                            if (vehicles.isEmpty()) {

                                System.out.println(
                                        "No vehicles found."
                                );

                            } else {

                                for (Vehicle vehicle :
                                        vehicles) {

                                    printVehicle(vehicle);
                                }
                            }
                        }

                        case 9 -> {

                            List<Vehicle> vehicles =
                                    vehicleDAO
                                            .getAvailableVehicles();

                            if (vehicles.isEmpty()) {

                                System.out.println(
                                        "No vehicles available."
                                );

                            } else {

                                for (Vehicle vehicle :
                                        vehicles) {

                                    printVehicle(vehicle);
                                }
                            }
                        }

                        case 10 -> {

                            System.out.print(
                                    "Enter vehicle ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Vehicle vehicle =
                                    vehicleDAO
                                            .getVehicleById(id);

                            if (vehicle == null) {

                                System.out.println(
                                        "Vehicle not found."
                                );

                                break;
                            }

                            System.out.print(
                                    "Vehicle number: "
                            );
                            vehicle.setVehicleNumber(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Brand: "
                            );
                            vehicle.setBrand(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Model: "
                            );
                            vehicle.setModel(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Vehicle type: "
                            );
                            vehicle.setVehicleType(
                                    scanner.nextLine()
                            );

                            System.out.print(
                                    "Price per day: "
                            );

                            vehicle.setPricePerDay(
                                    scanner.nextDouble()
                            );

                            scanner.nextLine();

                            vehicleDAO.updateVehicle(
                                    vehicle
                            );

                            System.out.println(
                                    "Vehicle updated successfully."
                            );
                        }

                        case 11 -> {

                            System.out.print(
                                    "Enter vehicle ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            vehicleDAO.deleteVehicle(id);

                            System.out.println(
                                    "Vehicle deleted successfully."
                            );
                        }

                        // =========================
                        // RENTAL
                        // =========================

                        case 12 -> {

                            System.out.print(
                                    "Enter customer ID: "
                            );

                            int customerId =
                                    scanner.nextInt();

                            scanner.nextLine();

                            Customer customer =
                                    customerDAO
                                            .getCustomerByID(
                                                    customerId
                                            );

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found."
                                );

                                break;
                            }

                            System.out.print(
                                    "Enter vehicle ID: "
                            );

                            int vehicleId =
                                    scanner.nextInt();

                            scanner.nextLine();

                            Vehicle vehicle =
                                    vehicleDAO
                                            .getVehicleById(
                                                    vehicleId
                                            );

                            if (vehicle == null) {

                                System.out.println(
                                        "Vehicle not found."
                                );

                                break;
                            }

                            if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {

                                System.out.println(
                                        "Vehicle is not available."
                                );

                                break;
                            }

                            System.out.print(
                                    "How many days do you want to rent? "
                            );

                            int days =
                                    scanner.nextInt();

                            scanner.nextLine();

                            if (days <= 0) {

                                System.out.println(
                                        "Days must be greater than zero."
                                );

                                break;
                            }

                            double totalAmount =
                                    days *
                                            vehicle.getPricePerDay();

                            Rental rental =
                                    new Rental();

                            rental.setCustomerId(
                                    customerId
                            );

                            rental.setVehicleId(
                                    vehicleId
                            );

                            rental.setRentalDate(
                                    LocalDate.now()
                            );

                            rental.setReturnDate(null);

                            rental.setTotalAmount(
                                    totalAmount
                            );

                            rental.setStatus(
                                    RentalStatus.ACTIVE
                            );

                            rentalDAO.createRental(
                                    rental
                            );

                            System.out.println();
                            System.out.println(
                                    "Rental created successfully."
                            );

                            System.out.println(
                                    "Customer: "
                                            + customer.getName()
                            );

                            System.out.println(
                                    "Vehicle: "
                                            + vehicle.getBrand()
                                            + " "
                                            + vehicle.getModel()
                            );

                            System.out.println(
                                    "Days: " + days
                            );

                            System.out.println(
                                    "Total amount: ₹"
                                            + totalAmount
                            );
                        }

                        case 13 -> {

                            System.out.print(
                                    "Enter rental ID: "
                            );

                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Rental rental =
                                    rentalDAO
                                            .getRentalById(id);

                            if (rental == null) {

                                System.out.println(
                                        "Rental not found."
                                );

                            } else {

                                printRental(rental);
                            }
                        }

                        case 14 -> {

                            List<Rental> rentals =
                                    rentalDAO
                                            .getAllRentals();

                            if (rentals.isEmpty()) {

                                System.out.println(
                                        "No rentals found."
                                );

                            } else {

                                for (Rental rental :
                                        rentals) {

                                    printRental(rental);
                                }
                            }
                        }

                        case 15 -> {

                            System.out.print(
                                    "Enter customer ID: "
                            );

                            int customerId =
                                    scanner.nextInt();

                            scanner.nextLine();

                            List<Rental> rentals =
                                    rentalDAO
                                            .getRentalsByCustomerId(
                                                    customerId
                                            );

                            if (rentals.isEmpty()) {

                                System.out.println(
                                        "No rentals found."
                                );

                            } else {

                                for (Rental rental :
                                        rentals) {

                                    printRental(rental);
                                }
                            }
                        }

                        case 16 -> {

                            List<Rental> rentals =
                                    rentalDAO
                                            .getActiveRentals();

                            if (rentals.isEmpty()) {

                                System.out.println(
                                        "No active rentals."
                                );

                            } else {

                                for (Rental rental :
                                        rentals) {

                                    printRental(rental);
                                }
                            }
                        }


                        case 17 -> {

                            System.out.println(
                                    "Thank you for using Vehicle Rental System."
                            );

                            return;
                        }

                        default -> System.out.println(
                                "Invalid choice."
                        );
                    }

                } catch (SQLException e) {

                    System.out.println(
                            "Database error: "
                                    + e.getMessage()
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Unable to connect to database: "
                            + e.getMessage()
            );
        }
    }

    // =========================
    // PRINT CUSTOMER
    // =========================

    private static void printCustomer(
            Customer customer) {

        System.out.println("----------------------------");
        System.out.println(
                "ID      : " + customer.getId()
        );
        System.out.println(
                "Name    : " + customer.getName()
        );
        System.out.println(
                "Email   : " + customer.getEmail()
        );
        System.out.println(
                "Phone   : " + customer.getPhone()
        );
        System.out.println(
                "Address : " + customer.getAddress()
        );
    }

    // =========================
    // PRINT VEHICLE
    // =========================

    private static void printVehicle(
            Vehicle vehicle) {

        System.out.println("----------------------------");
        System.out.println(
                "ID           : " + vehicle.getId()
        );
        System.out.println(
                "Number       : "
                        + vehicle.getVehicleNumber()
        );
        System.out.println(
                "Brand        : " + vehicle.getBrand()
        );
        System.out.println(
                "Model        : " + vehicle.getModel()
        );
        System.out.println(
                "Type         : "
                        + vehicle.getVehicleType()
        );
        System.out.println(
                "Price/Day    : ₹"
                        + vehicle.getPricePerDay()
        );
        System.out.println(
                "Status       : " + vehicle.getStatus()
        );
    }

    // =========================
    // PRINT RENTAL
    // =========================

    private static void printRental(
            Rental rental) {

        System.out.println("----------------------------");
        System.out.println(
                "Rental ID    : " + rental.getId()
        );
        System.out.println(
                "Customer ID  : "
                        + rental.getCustomerId()
        );
        System.out.println(
                "Vehicle ID   : "
                        + rental.getVehicleId()
        );
        System.out.println(
                "Rental Date  : " + rental.getRentalDate()
        );
        System.out.println(
                "Return Date  : " + rental.getReturnDate()
        );
        System.out.println(
                "Amount       : ₹"
                        + rental.getTotalAmount()
        );
        System.out.println(
                "Status       : "
                        + rental.getStatus()
        );
    }
}