package com.fsx.dto;

import com.fsx.enums.BusType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public record FilterReqDto(
        //Required fields
        @NotNull(message = "Please choose a source")
        @NotBlank(message = "Please choose a source")
        String source,

        @NotNull(message = "please choose a destination")
        @NotBlank(message = "please choose a destination")
        String destination,

        @NotNull(message = "please choose date of journey")
        @FutureOrPresent(message = "Choose a valid date of journey. It cannot be in the past")
        LocalDate dateOfJourney,

        //Optional fields
        BusType busType,

        //filtering based on time
        LocalTime startTime,
        LocalTime endTime,

        Double maxPrice

) {
}
