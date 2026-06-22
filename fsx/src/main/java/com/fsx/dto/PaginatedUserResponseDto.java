package com.fsx.dto;

import java.util.List;

public record PaginatedUserResponseDto(
        long totalRecords,
        int totalPages,
        List<UserDto> users
) {
}
