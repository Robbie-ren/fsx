package com.fsx.dto;

import java.util.List;

public record PaginatedBookingRespDto(
        long totalRecords,
        int totalPages,
        List<BookingDto> bookings
) {
}
