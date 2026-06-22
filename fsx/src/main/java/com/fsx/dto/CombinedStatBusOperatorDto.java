package com.fsx.dto;

import java.util.List;

public record CombinedStatBusOperatorDto(
        List<String> label,
        List<Long> count
) {
}
