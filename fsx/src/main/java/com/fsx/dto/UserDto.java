package com.fsx.dto;

import com.fsx.enums.AccountStatus;
import com.fsx.enums.Role;

public record UserDto(
        int userId,
        String username,
        Role role,
        AccountStatus status
) {
}
