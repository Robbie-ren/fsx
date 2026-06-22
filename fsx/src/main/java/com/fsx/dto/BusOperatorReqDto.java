package com.fsx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BusOperatorReqDto(
        String companyName,
        String officeAddress,
        String ownerName,
        String email,
        String phoneNumber,

        @NotNull
        @NotBlank
        @Size(min=4, message = "Username should be at-least 4 characters")
        String username
) {
}
