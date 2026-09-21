package com.kodewala.Model;

import com.kodewala.Model.Constant.RentalStatus;

import java.time.LocalDate;

public class Rental {
    private int id;
    private int customerId;
    private int vehicleId;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private double totalAmount;
    private RentalStatus status;
}
