package com.fsx.dto;

import java.util.List;

public record BookingMonthStatRespDto(
        String title,
        List<Integer> label,
        List<Long> data
) {
}
