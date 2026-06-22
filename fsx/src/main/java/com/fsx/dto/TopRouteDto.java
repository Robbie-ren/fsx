package com.fsx.dto;

public record TopRouteDto(
        String source,
        String destination,
        Double minPrice,
        Long bookingCount
) {
}
