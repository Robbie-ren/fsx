package com.fsx.service;

import com.fsx.dto.RouteReqDto;
import com.fsx.dto.TopRouteDto;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.model.Route;
import com.fsx.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    private Route route1;

    private Route route2;

    private Route route3;

    @BeforeEach
    public void sampleData(){
        route1=new Route();
        route1.setId(1);
        route1.setSourceCity("Chennai");
        route1.setDestinationCity("Bangalore");

        route2=new Route();
        route2.setId(2);
        route2.setSourceCity("Hyderabad");
        route2.setDestinationCity("Mumbai");
        route2.setActive(true);

    }

    @Test
    void getTopRoutes_ReturnTopThreeRoutes() {


        List<TopRouteDto> expectedRoutes = List.of(
                new TopRouteDto(route1.getSourceCity(), route1.getDestinationCity(), 150.0, 3L),
                new TopRouteDto(route2.getSourceCity(), route2.getDestinationCity(), 500.0, 12L)
        );


        when(routeRepository.findTopRoutes(any(Pageable.class)))
                .thenReturn(expectedRoutes);


        List<TopRouteDto> result = routeService.getTopRoutes();


        assertThat(result).hasSize(2);
        assertThat(result.getFirst().bookingCount()).isEqualTo(3L);
        assertThat(result.get(1).destination()).isEqualToIgnoringCase("mumbai");

        assertThat(result).isEqualTo(expectedRoutes);
    }

    @Test
    void getById_routeExists(){
        when(routeRepository.findById(20)).thenReturn(Optional.of(route1));

        assertThat(routeService.getById(20).getId()).isEqualTo(1);
        assertThat(routeService.getById(20).getSourceCity()).isEqualToIgnoringCase("chennai");
    }

    @Test
    void getById_routeDoesNotExist(){
        when(routeRepository.findById(20)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> routeService.getById(20)).
                isInstanceOf(ResourceNotFoundException.class).
                hasMessage("Invalid route id...");

        verify(routeRepository, times(1)).findById(20);
    }

    @Test
    void deleteRoute_softDelete(){
        when(routeRepository.findById(100)).thenReturn(Optional.of(route2));

        routeService.softDelete(100);

        assertThat(route2.isActive()).isFalse();
        verify(routeRepository, times(1)).save(route2);
        verify(routeRepository, times(1)).findById(100);
    }

    @Test
    void delete_mustDeleteAndReturnNothing(){
        when(routeRepository.findById(10)).thenReturn(Optional.of(route2));


        doNothing().when(routeRepository).deleteById(10);

        routeService.delete(10);


        verify(routeRepository, times(1)).deleteById(10);
        verify(routeRepository, times(1)).findById(10);
    }


    @Test
    void addRoute_mustSaveAndReturnRoute(){
        when(routeRepository.save(any(Route.class))).thenReturn(route1);

        RouteReqDto dto = new RouteReqDto("Hyderabad", "Chennai", 630);
        Route actualResult = routeService.addRoute(dto);
        assertThat(actualResult.getSourceCity()).isEqualToIgnoringCase(route1.getSourceCity());
        assertThat(actualResult.getDistanceInKm()).isEqualTo(route1.getDistanceInKm());

        verify(routeRepository, times(1)).save(any(Route.class));
    }


}
