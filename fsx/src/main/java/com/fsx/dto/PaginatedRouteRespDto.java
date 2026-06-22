package com.fsx.dto;

import com.fsx.model.Route;

import java.util.List;

public record PaginatedRouteRespDto(
        long totalRecords,
        int totalPages,
        List<RouteRespDto> routes
) {
}
