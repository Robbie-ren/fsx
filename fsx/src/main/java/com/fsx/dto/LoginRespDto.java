package com.fsx.dto;

import com.fsx.enums.Role;

public record LoginRespDto(
        int id,
        String username,
        String role
) {
}
