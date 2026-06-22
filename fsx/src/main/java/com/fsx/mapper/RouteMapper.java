package com.fsx.mapper;

import com.fsx.dto.RouteReqDto;
import com.fsx.dto.RouteRespDto;
import com.fsx.model.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    public static Route mapToEntity(RouteReqDto dto){
        Route route = new Route();
        route.setSourceCity(dto.sourceCity());
        route.setDestinationCity(dto.destinationCity());
        route.setDistanceInKm(dto.distanceInKm());
        return route;
    }

    public RouteRespDto mapToDto(Route route){
        return new RouteRespDto(route.getId(),
                route.getSourceCity(),
                route.getDestinationCity(),
                route.getDistanceInKm(),
                route.isActive());
    }
}

