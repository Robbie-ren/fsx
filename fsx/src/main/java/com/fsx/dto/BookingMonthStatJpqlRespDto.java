package com.fsx.dto;

public record BookingMonthStatJpqlRespDto(
        Integer month,
        long numberOfBookings
) {
}
