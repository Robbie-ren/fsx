package com.fsx.dto;

public record RouteRespDto(
        int routeId,
        String sourceCity,
        String destinationCity,
        double distanceInKm,
        boolean isActive
) {
}
