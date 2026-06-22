package com.fsx.dto;

import java.util.List;

public record CombinedStatAdminDto(
        List<String> label,
        List<Long> count
) {
}
