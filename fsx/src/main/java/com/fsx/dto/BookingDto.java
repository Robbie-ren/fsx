package com.fsx.dto;

import com.fsx.enums.BookingStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;


public record BookingDto(
        int bookingId,
        String passengerName,
        Instant bookingDate,
        String source,
        String destination,
        LocalDate journeyDate,
        LocalTime departureTime,
        String busName,
        String busNumber,
        double totalAmount,
        BookingStatus bookingStatus,
        int seatCount
)
{

}
