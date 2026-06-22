package com.fsx.dto;

import com.fsx.model.Booking;

import java.util.List;

public record BookingRespDto(
        long totalRecords,
        int totalPages,
        List<Booking> data
) {
}
