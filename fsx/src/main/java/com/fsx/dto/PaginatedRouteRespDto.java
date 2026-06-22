package com.fsx.dto;



import java.util.List;

public record PaginatedRouteRespDto(
        long totalRecords,
        int totalPages,
        List<RouteRespDto> routes
) {
}
