package com.fsx.controller;

import com.fsx.dto.PassengerEditProfileDto;
import com.fsx.dto.PassengerReqDto;
import com.fsx.dto.PassengerRespDto;
import com.fsx.model.Passenger;
import com.fsx.service.PassengerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PassengerController {


    private final PassengerService passengerService;

    @PostMapping("/post")
    public Passenger postPassenger(@Valid @RequestBody PassengerReqDto dto){

        return passengerService.post(dto);
    }

    @GetMapping("/admin/getAllPassengers")
    public List<PassengerRespDto> getAllPassengers(@RequestParam(defaultValue = "0", required = false)int page, @RequestParam(defaultValue = "10", required = false)int size){
        return passengerService.getAllPassengers(page, size);
    }

    @GetMapping("/admin/passengerById")
    public PassengerRespDto getPassengerById(int passengerId){
        return passengerService.getById(passengerId);
    }

    @PostMapping("/id/upload/{id}")
    public void upload(@PathVariable int id,
                       @RequestParam("file") MultipartFile file) throws IOException {
        //file is the actual doc/image user is uploading.


        passengerService.upload(id, file);
    }

    @PutMapping("/update")
    public Passenger editProfile(Principal principal, @RequestBody PassengerEditProfileDto dto){
        return passengerService.editProfile(principal.getName(), dto);

    }
}
