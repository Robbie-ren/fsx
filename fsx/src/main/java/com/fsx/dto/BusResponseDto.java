package com.fsx.dto;

import com.fsx.enums.BusType;

public record BusResponseDto(
        int busId,
        String busName,
        String busNumber,
        BusType busType,
        int totalSeats,
        boolean isActive

) {
}
