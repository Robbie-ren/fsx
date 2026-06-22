package com.fsx.controller;

import com.fsx.dto.BusOperatorReqDto;
import com.fsx.dto.CombinedStatBusOperatorDto;
import com.fsx.dto.OperatorEditProfileDto;
import com.fsx.dto.PassengerEditProfileDto;
import com.fsx.model.BusOperator;
import com.fsx.model.Passenger;
import com.fsx.service.BusOperatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/operator")
@CrossOrigin(origins = "http://localhost:5173")
public class BusOperatorController {
    private final BusOperatorService busOperatorService;

    @PostMapping("/add")
    public void addBusOperator(@Valid @RequestBody BusOperatorReqDto dto){
        busOperatorService.addBusOperator(dto);
    }

    @GetMapping("/combined-stats")
    public CombinedStatBusOperatorDto getCombinedStats(Principal principal){
        String username = principal.getName();
        return busOperatorService.getCombinedStats(username);
    }

    @PutMapping("/update")
    public BusOperator editProfile(Principal principal, @RequestBody OperatorEditProfileDto dto){
        return busOperatorService.editProfile(principal.getName(), dto);

    }
}
