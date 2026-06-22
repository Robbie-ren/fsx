package com.fsx.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PassengerReqDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        String gender,
        @NotNull
        @Min(value = 1, message = "Age must be greater than 0")
        int age,
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String phoneNumber,
        @NotNull
        @NotBlank
        @Size(min = 4, message = "Username should be at-least 4 characters")
        String username,
        @NotNull
        @NotBlank
        @Size(min = 4, message = "Password should be at-least 4 characters")
        String password
) {
}
