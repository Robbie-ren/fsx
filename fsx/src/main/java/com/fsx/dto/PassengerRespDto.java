package com.fsx.dto;

import com.fsx.enums.AccountStatus;

import java.time.Instant;

public record PassengerRespDto(
        int userId,
        int passengerId,
        String gender,
        String name,
        String phoneNumber,
        AccountStatus accountStatus,
        Instant createdAt
) {
}
