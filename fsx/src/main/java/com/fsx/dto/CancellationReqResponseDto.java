package com.fsx.dto;

import com.fsx.enums.RequestStatus;

import java.time.Instant;
import java.time.LocalDate;

public record CancellationReqResponseDto(
        int requestId,
        int passengerId,
        String passengerName,
        String reason,
        Instant requestedAt,
        String source,
        String destination,
        LocalDate journeyDate,
        int seats,
        double refundAmount,
        RequestStatus status
) {
}
