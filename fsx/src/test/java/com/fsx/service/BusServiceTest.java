package com.fsx.service;



import com.fsx.exception.ResourceNotFoundException;

import com.fsx.model.Bus;
import com.fsx.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    private Bus bus1;

    private Bus bus2;

    @BeforeEach
    public void SampleData(){
        bus1 = new Bus();
        bus1.setBusName("Royal bus");
        bus1.setBusNumber("TN123");

        bus2 = new Bus();
        bus2.setBusName("Super bus");
        bus2.setBusNumber("TN321");
    }


    @Test
    void getById_busExists(){
        when(busRepository.findById(400)).thenReturn(Optional.of(bus1));

        assertThat(busService.getById(400).getBusName()).isEqualTo(bus1.getBusName());
        assertThat(busService.getById(400).getBusNumber()).isEqualTo(bus1.getBusNumber());
        assertThat(busService.getById(400)).isEqualTo(bus1);
    }

    @Test
    void getById_busDoesNotExist(){
        when(busRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> busService.getById(100)).
                isInstanceOf(ResourceNotFoundException.class).
                hasMessage("Invalid bus id...");

        verify(busRepository, times(1)).findById(100);
    }


    @Test
    void deleteRoute_softDelete(){
        when(busRepository.findById(100)).thenReturn(Optional.of(bus2));

        busService.softDelete(100);

        assertThat(bus2.isActive()).isFalse();
        verify(busRepository, times(1)).save(bus2);
        verify(busRepository, times(1)).findById(100);
    }



}
