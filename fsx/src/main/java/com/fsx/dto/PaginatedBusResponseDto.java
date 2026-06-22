package com.fsx.dto;

import java.util.List;

public record PaginatedBusResponseDto(
        long totalRecords,
        int totalPages,
        List<BusResponseDto> buses
) {
}
