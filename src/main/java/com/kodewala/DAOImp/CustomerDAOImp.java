package com.kodewala.DAOImp;

import com.kodewala.DAO.CustomerDAO;
import com.kodewala.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImp implements CustomerDAO {

    private final Connection connection;

    public CustomerDAOImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = """
                INSERT INTO customers (name, email, phone, customer_address) VALUES (?, ?, ?, ?)
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());


            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getCustomerByID(int id) {

        String sql = """
                SELECT * FROM customers WHERE id = ?
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Customer customer = new Customer();

                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("customer_address"));

                    return customer;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Customer> getAllCustomer() {
        String sql = """
                SELECT * FROM customers
                """;

        List<Customer> customers = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("customer_address"));

                customers.add(customer);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = """
                UPDATE customers
                SET name = ?, email = ?, phone = ?, customer_address = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomerById(int id){
        String sql = """
                DELETE FROM customers
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
