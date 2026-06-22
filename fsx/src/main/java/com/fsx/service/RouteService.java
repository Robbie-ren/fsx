package com.fsx.service;

import com.fsx.dto.PaginatedRouteRespDto;
import com.fsx.dto.RouteReqDto;
import com.fsx.dto.RouteRespDto;
import com.fsx.dto.TopRouteDto;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.RouteMapper;
import com.fsx.model.Route;
import com.fsx.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public Route getById(int id) {
        return routeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid route id..."));
    }

    public Route addRoute(RouteReqDto dto) {
        Route route = RouteMapper.mapToEntity(dto);
        route.setActive(true);
        return routeRepository.save(route);
    }

    public List<RouteRespDto> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routes.stream().map(routeMapper::mapToDto).toList();
    }

    public PaginatedRouteRespDto getRoutes(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Route> pages;
        if(keyword == null || keyword.isBlank()) {
            pages = routeRepository.findAllActive(pageable);
        }
        else {
            pages = routeRepository.getRoutes(keyword, pageable);
        }
        List<RouteRespDto> routes = pages.getContent().stream().map(routeMapper::mapToDto).toList();
        return new PaginatedRouteRespDto(pages.getTotalElements(), pages.getTotalPages(), routes);

    }

    public void delete(int id){
        getById(id);
        routeRepository.deleteById(id);
    }

    public void softDelete(int id) {
        Route route = getById(id);
        route.setActive(false);
        routeRepository.save(route);
    }

    public List<String> searchCities(String query) {

        if (query == null || query.length() < 2) {
            return new ArrayList<>();
        }

        List<String> sources = routeRepository.searchSourceCities(query);
        List<String> destinations = routeRepository.searchDestinationCities(query);

        Set<String> result = new HashSet<>();
        result.addAll(sources);
        result.addAll(destinations);

        return result.stream()
                .sorted()
                .toList();
    }

    public List<TopRouteDto> getTopRoutes() {
        return routeRepository.findTopRoutes(PageRequest.of(0, 3));
    }
}