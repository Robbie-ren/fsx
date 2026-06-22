package com.fsx.controller;

import com.fsx.dto.CombinedStatAdminDto;
import com.fsx.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    public final AdminService adminService;

    @GetMapping("/combined-stat")
    public CombinedStatAdminDto getCombinedStat(){
        return adminService.getCombinedStat();

    }
}
