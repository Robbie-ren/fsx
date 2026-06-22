package com.fsx.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RouteReqDto(
        @NotNull(message = "source should be filled")
        @NotBlank(message = "source cannot be blank")
        String sourceCity,
        @NotNull(message = "destination should be filled")
        @NotBlank(message = "destination cannot be blank")
        String destinationCity,
        @NotNull(message = "this field cannot be blank")
        double distanceInKm
) {
}
