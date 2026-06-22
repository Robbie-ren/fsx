package com.fsx.dto;

import com.fsx.enums.Role;

import java.time.Instant;

public record BusOperatorUserProfileRespDto(
        int userId,
        int busOperatorId,
        String username,
        String companyName,
        String ownerName,
        String officeAddress,
        String email,
        String phoneNumber,
        Role role,
        Instant createdAt
) {
}
