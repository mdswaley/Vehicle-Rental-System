package com.kodewala.DAO;

import com.kodewala.Model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer) throws SQLException;

    Customer getCustomerByID(int id) throws SQLException;

    List<Customer> getAllCustomer() throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void deleteCustomerById(int id) throws SQLException;
}
