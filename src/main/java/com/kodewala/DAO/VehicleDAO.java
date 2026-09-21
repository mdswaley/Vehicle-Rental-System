package com.kodewala.DAO;

import com.kodewala.Model.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehicleDAO {
    void addVehicle(Vehicle vehicle) throws SQLException;

    Vehicle getVehicleById(int id) throws SQLException;

    List<Vehicle> getAllVehicles() throws SQLException;

    List<Vehicle> getAvailableVehicles() throws SQLException;

    void updateVehicle(Vehicle vehicle) throws SQLException;

    void deleteVehicle(int id) throws SQLException;
}
