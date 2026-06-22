package com.fsx.repository;

import com.fsx.dto.FilterReqDto;
import com.fsx.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleRepositoryCustom {
    Page<Schedule> getSchedulesByFilter(
            FilterReqDto dto,
            Pageable pageable
    );

    List<Schedule> getSchedulesByFilterV2(
            FilterReqDto dto
    );
}
