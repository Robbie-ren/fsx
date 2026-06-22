package com.fsx.dto;

import java.util.List;

public record PaginatedScheduleResponseDto(
        long totalRecords,
        int totalPages,
        List<ScheduleOperatorRespDto> schedules
) {
}
