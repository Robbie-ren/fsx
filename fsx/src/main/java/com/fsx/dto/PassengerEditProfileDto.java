package com.fsx.dto;

public record PassengerEditProfileDto(

        String name,
        Integer age,
        String gender,
        String email,
        String phoneNumber
) {
}
