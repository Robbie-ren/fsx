package com.fsx.dto;

public record ChangePasswordDto(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {
}
