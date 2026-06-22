package com.fsx.controller;

import com.fsx.dto.LoginRespDto;
import com.fsx.dto.TokenDto;
import com.fsx.model.User;
import com.fsx.service.UserService;
import com.fsx.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtUtility jwtUtility;

    @GetMapping("/login")
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username, token);
    }

    @GetMapping("/user-details")
    public LoginRespDto userDetails(Principal principal){
        String loggedInUsername = principal.getName();
        User user = (User) userService.loadUserByUsername(loggedInUsername);
        return new LoginRespDto(user.getId(), user.getUsername(), user.getRole().toString());
    }
}
