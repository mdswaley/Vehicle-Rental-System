package com.kodewala.DAOImp;

import com.kodewala.DAO.VehicleDAO;
import com.kodewala.Model.Constant.VehicleStatus;
import com.kodewala.Model.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VehicleDAOImp implements VehicleDAO {

    private final Connection connection;

    VehicleDAOImp(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String sql = """
                INSERT INTO vehicle (vehicleNumber, brand, model, vehicleType, pricePerDay, status) VALUES (?, ?, ?, ?, ?, ?)
                """;

    }

    @Override
    public Vehicle getVehicleById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Vehicle> getAllVehicles() throws SQLException {
        return List.of();
    }

    @Override
    public List<Vehicle> getAvailableVehicles() throws SQLException {
        return List.of();
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {

    }

    @Override
    public void deleteVehicle(int id) throws SQLException {

    }
}
