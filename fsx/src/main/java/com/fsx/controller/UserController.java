package com.fsx.controller;

import com.fsx.dto.BusOperatorUserProfileRespDto;
import com.fsx.dto.ChangePasswordDto;
import com.fsx.dto.PaginatedUserResponseDto;
import com.fsx.dto.PassengerUserProfileRespDto;
import com.fsx.enums.AccountStatus;
import com.fsx.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @GetMapping("/passenger/userProfile")
    public PassengerUserProfileRespDto getPassengerUserProfile(Principal principal){
        String username = principal.getName();
        return userService.getPassengerUserProfile(username);
    }

    @GetMapping("/busOperator/userProfile")
    public BusOperatorUserProfileRespDto getBusOperatorUserProfile(Principal principal){
        String username = principal.getName();
        return userService.getBusOperatorUserProfile(username);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordDto dto,
            Principal principal
    ) {
        userService.changePassword(principal.getName(), dto);

        return "Password changed successfully";
    }

    @GetMapping("/all")
    public PaginatedUserResponseDto getUsers( @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "10", required = false) int size,
                                              @RequestParam AccountStatus status,
                                              @RequestParam(required = false) String keyword){

        return userService.getUsers(page, size, status, keyword);
    }

    @PutMapping("/disable/{id}")
    public void disable(@PathVariable int id){
        userService.disable(id);
    }

    @PutMapping("/enable/{id}")
    public void enable(@PathVariable int id){
        userService.enable(id);
    }


}
