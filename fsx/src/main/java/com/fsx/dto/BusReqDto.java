package com.fsx.dto;

import com.fsx.enums.BusType;

public record BusReqDto(
        String busName,
        String busNumber,
        BusType busType,
        int totalSeats
) {
}
