package com.fsx.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketResponseDto(
        int ticketId,
        int bookingId,
        int seatNumber,
        String passengerName,
        int passengerAge,
        LocalDate journeyDate,
        LocalTime departureTime,
        LocalTime arrivalTime,
        String source,
        String destination,
        String busName,
        String busNumber,
        double price
) {
}
