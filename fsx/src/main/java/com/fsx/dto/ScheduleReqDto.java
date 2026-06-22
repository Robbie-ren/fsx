package com.fsx.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleReqDto(
        LocalDate departureDate,
        LocalTime departureTime,
        LocalTime arrivalTime,
        double price,
        int busId,
        int routeId
) {
}
