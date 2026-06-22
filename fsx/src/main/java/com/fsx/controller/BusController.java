package com.fsx.controller;

import com.fsx.dto.BusReqDto;
import com.fsx.dto.BusResponseDto;
import com.fsx.dto.PaginatedBusResponseDto;
import com.fsx.enums.BusType;
import com.fsx.model.Bus;
import com.fsx.service.BusService;
import com.fsx.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bus")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BusController {

    private final BusService busService;

    @PostMapping("/add")
    public void addBus(@RequestBody BusReqDto dto, Principal principal){
        String loggedInUserName = principal.getName();
        busService.addBus(dto, loggedInUserName);
    }

    @GetMapping("/getAllByBusOperator")
    public PaginatedBusResponseDto getAllBuses(Principal principal, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @RequestParam(required = false) boolean active){
        String loggedInUsername = principal.getName();
        return busService.getAllBuses(loggedInUsername, page, size, active);
    }

    @DeleteMapping("/delete/{busId}")
    public void deleteBusById(@PathVariable int busId, Principal principal){
        String loggedInUserName = principal.getName();
        busService.deleteBusById(busId, loggedInUserName);
    }

    @DeleteMapping("/soft-delete/{id}")
    public void softDelete(@PathVariable int id){
        busService.softDelete(id);
    }

    @GetMapping("/types")
    public List<BusType> getTypes(){
        return busService.getTypes();
    }
}
