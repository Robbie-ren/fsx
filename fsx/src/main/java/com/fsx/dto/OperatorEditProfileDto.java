package com.fsx.dto;

public record OperatorEditProfileDto(
        String companyName,
        String ownerName,
        String officeAddress,
        String email,
        String phoneNumber
) {
}
