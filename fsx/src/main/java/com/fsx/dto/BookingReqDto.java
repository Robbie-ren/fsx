package com.fsx.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookingReqDto(
        @NotNull
        int scheduleId,

        @NotEmpty
        List<PassengerSeatDto> seatNumbers
) {
}
