package com.fsx.controller;

import com.fsx.dto.*;
import com.fsx.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/get-schedule/by_source_destination")
    public PaginatedScheduleResponse searchSchedules(@Valid @RequestBody FilterReqDto dto,
                                                                        @RequestParam(defaultValue = "0", required = false) int page,
                                                                        @RequestParam(defaultValue = "10", required = false) int size){
        return scheduleService.searchSchedules(dto, page, size);
    }

    @PostMapping("/get-schedule/by_source_destination/v2")
    public List<ScheduleRespDto> searchSchedulesV2(@Valid @RequestBody FilterReqDto dto){
        return scheduleService.searchSchedulesV2(dto);
    }

    @PostMapping("/add")
    public void addSchedule(@RequestBody ScheduleReqDto dto, Principal principal){
        String loggedInUserName = principal.getName();
        scheduleService.addSchedule(dto, loggedInUserName);
    }

    @GetMapping("/getAll")
    public PaginatedScheduleResponseDto getAllSchedules(Principal principal, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam(required = false) String keyword, @RequestParam(required = false)boolean active){
        String username = principal.getName();
        return scheduleService.getAllSchedules(username, page, size, keyword, active);
    }

    @DeleteMapping("/soft-delete/{id}")
    public void softDelete(@PathVariable int id){
        scheduleService.softDelete(id);
    }
}
