package com.fsx.controller;

import com.fsx.dto.PaginatedRouteRespDto;
import com.fsx.dto.RouteReqDto;
import com.fsx.dto.RouteRespDto;
import com.fsx.dto.TopRouteDto;
import com.fsx.model.Route;
import com.fsx.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {
    private final RouteService routeService;


    @PostMapping("/add")
    public Route addRoute(@RequestBody RouteReqDto dto) {
        return routeService.addRoute(dto);
    }

    @GetMapping("/getAll")
    public List<RouteRespDto> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/searchRoutes")
    public PaginatedRouteRespDto getRoutes(@RequestParam(required = false) String keyword,
                                           @RequestParam(defaultValue = "0", required = false) int page,
                                           @RequestParam(defaultValue = "10", required = false) int size)
    {
        return routeService.getRoutes(keyword, page, size);
    }

    @DeleteMapping("/soft-delete/{id}")
    public void softDelete(@PathVariable int id){
        routeService.softDelete(id);
    }

    @GetMapping("/search")
    public List<String> searchCities(@RequestParam String query) {
        return routeService.searchCities(query);
    }

    @GetMapping("/top")
    public List<TopRouteDto> getTopRoutes() {
        return routeService.getTopRoutes();
    }
}




