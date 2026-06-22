package com.fsx.dto;

import java.util.List;

public record PaginatedScheduleResponse(
        long totalRecords,
        int totalPages,
        List<ScheduleRespDto> schedules
) {
}
