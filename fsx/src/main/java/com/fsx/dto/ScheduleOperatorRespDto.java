package com.fsx.dto;

import com.fsx.model.Bus;
import com.fsx.model.Route;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleOperatorRespDto(
         int id,

         LocalDate departureDate,

         LocalTime departureTime,

         LocalTime arrivalTime,

         int availableSeats,

         double price,

        int busId,
         String busNumber,
         String busName,
         String sourceCity,
         String destinationCity,
         boolean status
) {
}
