package com.kodewala.DAO;

import com.kodewala.Model.Rental;
import com.kodewala.Model.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface RentalDAO {
    void createRental(Rental rental) throws SQLException;

    Rental getRentalById(int id) throws SQLException;

    List<Rental> getAllRentals() throws SQLException;

    List<Rental> getRentalsByCustomerId(int customerId) throws SQLException;

    List<Rental> getActiveRentals() throws SQLException;
}
