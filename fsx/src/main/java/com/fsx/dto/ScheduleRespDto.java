package com.fsx.dto;

import com.fsx.enums.BusType;

import java.time.LocalTime;

public record ScheduleRespDto(
        int scheduleId,
        String busOperatorName,
        LocalTime departureTime,
        LocalTime arrivalTime,
        int totalSeats,
        int availableSeats,
        double price,
        BusType busType,
        double distance
) {
}
