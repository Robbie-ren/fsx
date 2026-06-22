package com.fsx.service;

import com.fsx.dto.*;
import com.fsx.exception.InvalidOwnershipException;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.ScheduleMapper;
import com.fsx.model.Bus;
import com.fsx.model.Route;
import com.fsx.model.Schedule;
import com.fsx.repository.ScheduleRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final BusService busService;
    private final RouteService routeService;


    public PaginatedScheduleResponse searchSchedules(FilterReqDto dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> pages = scheduleRepository.getSchedulesByFilter(dto, pageable);
        List<ScheduleRespDto> list = pages.getContent().stream().map(scheduleMapper::mapToDto).toList();
        return new PaginatedScheduleResponse(pages.getTotalElements(), pages.getTotalPages(), list);
    }


    public Schedule getById(int scheduleId) {
       return scheduleRepository.findById(scheduleId).orElseThrow(() -> new ResourceNotFoundException("Invalid Schedule ID..."));
    }


    public void decrementAvailableSeats(int scheduleId, int seatCount) {
        scheduleRepository.decrementAvailableSeats(scheduleId, seatCount);
    }


    public void addSchedule(ScheduleReqDto dto, String loggedInUserName) {
        Bus bus = busService.getById(dto.busId());
        if(!bus.getBusOperator().getUser().getUsername().equals(loggedInUserName)){
            throw new InvalidOwnershipException("You do not own this bus...");
        }
        Route route = routeService.getById(dto.routeId());
        Schedule schedule = scheduleMapper.mapToEntity(dto, bus, route);
        schedule.setActive(true);
        scheduleRepository.save(schedule);
    }


    public PaginatedScheduleResponseDto getAllSchedules(String username, int page, int size, String keyword, boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedules = scheduleRepository.searchSchedules(keyword, active, username, pageable);
        List<ScheduleOperatorRespDto> list =  schedules.getContent().stream().map(scheduleMapper :: mapToRespDto).toList();
        return new PaginatedScheduleResponseDto(schedules.getTotalElements(), schedules.getTotalPages(), list);
    }

    public List<ScheduleRespDto> searchSchedulesV2(@Valid FilterReqDto dto) {
        List<Schedule> list = scheduleRepository.getSchedulesByFilterV2(dto);
        return list.stream().map(scheduleMapper::mapToDto).toList();

    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public void softDelete(int id) {
        Schedule schedule = getById(id);
        schedule.setActive(false);
        scheduleRepository.save(schedule);
    }
}
