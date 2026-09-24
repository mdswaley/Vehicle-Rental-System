package com.kodewala.DAOImp;

import com.kodewala.DAO.RentalDAO;
import com.kodewala.Model.Constant.RentalStatus;
import com.kodewala.Model.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAOImp implements RentalDAO {

    private final Connection connection;

    public RentalDAOImp(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createRental(Rental rental) throws SQLException {
        String checkVehicleSql = """
                SELECT status
                FROM vehicles
                WHERE id = ?
                """;

        String insertRentalSql = """
                INSERT INTO rentals
                (customer_id, vehicle_id, rental_date,
                 return_date, total_amount, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String updateVehicleSql = """
                UPDATE vehicles
                SET status = 'RENTED'
                WHERE id = ?
                """;

        boolean oldAutoCommit = connection.getAutoCommit();

        try {

            connection.setAutoCommit(false);

            // 1. Check vehicle
            try (PreparedStatement ps = connection.prepareStatement(checkVehicleSql)) {

                ps.setInt(1, rental.getVehicleId());

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException("Vehicle not found.");
                    }

                    String status = rs.getString("status");

                    if (!"AVAILABLE".equalsIgnoreCase(status)) {
                        throw new SQLException(
                                "Vehicle is not available."
                        );
                    }
                }
            }

            // 2. Create rental
            try (PreparedStatement ps = connection.prepareStatement(insertRentalSql)) {

                ps.setInt(1, rental.getCustomerId());
                ps.setInt(2, rental.getVehicleId());
                ps.setDate(3, Date.valueOf(rental.getRentalDate()));

                if (rental.getReturnDate() != null) {
                    ps.setDate(4, Date.valueOf(rental.getReturnDate()));
                } else {
                    ps.setNull(4, Types.DATE);
                }

                ps.setDouble(5, rental.getTotalAmount());
                ps.setString(6, rental.getStatus().name());

                ps.executeUpdate();
            }

            // 3. Change vehicle status
            try (PreparedStatement ps = connection.prepareStatement(updateVehicleSql)) {

                ps.setInt(1, rental.getVehicleId());

                ps.executeUpdate();
            }

            // 4. Everything successful
            connection.commit();

        } catch (Exception e) {

            // Something failed → undo everything
            connection.rollback();

            throw e;

        } finally {

            connection.setAutoCommit(oldAutoCommit);
        }
    }


    @Override
    public Rental getRentalById(int id) throws SQLException {
        String sql = """
                SELECT *
                FROM rentals
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapRental(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Rental> getAllRentals() throws SQLException {
        String sql = """
                SELECT *
                FROM rentals
                """;

        List<Rental> rentals = new ArrayList<>();

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rentals.add(mapRental(rs));
            }
        }

        return rentals;
    }

    @Override
    public List<Rental> getRentalsByCustomerId(int customerId) throws SQLException {
        String sql = """
                SELECT *
                FROM rentals
                WHERE customer_id = ?
                """;

        List<Rental> rentals = new ArrayList<>();

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        }

        return rentals;
    }

    @Override
    public List<Rental> getActiveRentals() throws SQLException {
        String sql = """
                SELECT *
                FROM rentals
                WHERE status = ?
                """;

        List<Rental> rentals = new ArrayList<>();

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, RentalStatus.ACTIVE.name());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    rentals.add(mapRental(rs));
                }
            }
        }

        return rentals;
    }

    private Rental mapRental(ResultSet rs)
            throws SQLException {

        Rental rental = new Rental();

        rental.setId(rs.getInt("id"));
        rental.setCustomerId(
                rs.getInt("customer_id")
        );
        rental.setVehicleId(
                rs.getInt("vehicle_id")
        );

        Date rentalDate =
                rs.getDate("rental_date");

        if (rentalDate != null) {
            rental.setRentalDate(
                    rentalDate.toLocalDate()
            );
        }

        Date returnDate =
                rs.getDate("return_date");

        if (returnDate != null) {
            rental.setReturnDate(
                    returnDate.toLocalDate()
            );
        }

        rental.setTotalAmount(
                rs.getDouble("total_amount")
        );

        rental.setStatus(
                RentalStatus.valueOf(
                        rs.getString("status")
                )
        );

        return rental;
    }
}
