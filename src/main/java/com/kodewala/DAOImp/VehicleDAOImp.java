package com.kodewala.DAOImp;

import com.kodewala.DAO.VehicleDAO;
import com.kodewala.Model.Constant.VehicleStatus;
import com.kodewala.Model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImp implements VehicleDAO {

    private final Connection connection;

    public VehicleDAOImp(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String sql = """
                INSERT INTO vehicles (vehicleNumber, brand, model, vehicleType, pricePerDay, status) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, vehicle.getVehicleNumber());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setString(4, vehicle.getVehicleType());
            ps.setDouble(5, vehicle.getPricePerDay());
            ps.setString(6, vehicle.getStatus().name());

            ps.executeUpdate();
        }

    }

    @Override
    public Vehicle getVehicleById(int id) throws SQLException {
        String sql = """ 
                SELECT * FROM vehicles WHERE ID = ?
                """;

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    mapVehicle(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Vehicle> getAllVehicles() throws SQLException {
        String sql = """
                SELECT *
                FROM vehicles
                """;

        List<Vehicle> vehicles = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapVehicle(rs));
            }
        }

        return vehicles;
    }

    @Override
    public List<Vehicle> getAvailableVehicles() throws SQLException {

        String sql = """
                SELECT *
                FROM vehicles
                WHERE status = ?
                """;

        List<Vehicle> vehicles = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, VehicleStatus.AVAILABLE.name());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    vehicles.add(mapVehicle(rs));
                }
            }
        }

        return vehicles;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = """
                UPDATE vehicles
                SET vehicle_number = ?,
                    brand = ?,
                    model = ?,
                    vehicle_type = ?,
                    price_per_day = ?,
                    status = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, vehicle.getVehicleNumber());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setString(4, vehicle.getVehicleType());
            ps.setDouble(5, vehicle.getPricePerDay());
            ps.setString(6, vehicle.getStatus().name());
            ps.setInt(7, vehicle.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteVehicle(int id) throws SQLException {
        String sql = """
                DELETE FROM vehicles
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {

        Vehicle vehicle = new Vehicle();

        vehicle.setId(rs.getInt("id"));
        vehicle.setVehicleNumber(rs.getString("vehicle_number"));
        vehicle.setBrand(rs.getString("brand"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setVehicleType(rs.getString("vehicle_type"));
        vehicle.setPricePerDay(rs.getDouble("price_per_day"));

        vehicle.setStatus(VehicleStatus.valueOf(
                rs.getString("status")));

        return vehicle;
    }
}
