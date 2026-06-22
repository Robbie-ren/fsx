package com.fsx.dto;

import com.fsx.enums.Role;

import java.time.Instant;

public record PassengerUserProfileRespDto(
        int userId,
        int passengerId,
        String username,
        String name,
        int age,
        String gender,
        String email,
        String phoneNumber,
        Role role,
        Instant createdAt,
        String idProofUrl
) {

}
