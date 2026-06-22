package com.fsx.service;

import com.fsx.dto.BusReqDto;
import com.fsx.dto.BusResponseDto;
import com.fsx.dto.PaginatedBusResponseDto;
import com.fsx.enums.BusType;
import com.fsx.exception.InvalidOwnershipException;
import com.fsx.exception.ResourceNotFoundException;
import com.fsx.mapper.BusMapper;
import com.fsx.model.Bus;
import com.fsx.model.BusOperator;
import com.fsx.model.Route;
import com.fsx.repository.BusRepository;
import com.fsx.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final BusOperatorService busOperatorService;
    private final BusMapper busMapper;
    private final ScheduleRepository scheduleRepository;

    public Bus getById(int id) {
        return busRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid bus id..."));
    }

    public void addBus(BusReqDto dto, String loggedInUserName) {
        BusOperator busOperator = busOperatorService.getByUserName(loggedInUserName);
        Bus bus = busMapper.mapToEntity(dto, busOperator);
        bus.setActive(true);
        busRepository.save(bus);
    }

    public PaginatedBusResponseDto getAllBuses(String loggedInUsername, int page, int size, boolean active) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> buses = busRepository.findByBusOperatorUserUsernameAndActive(loggedInUsername, active, pageable);
        List<BusResponseDto> list = buses.getContent().stream().map(busMapper :: mapToDto).toList();
        return new PaginatedBusResponseDto(buses.getTotalElements(), buses.getTotalPages(), list);
    }

    public void deleteBusById(int busId, String loggedInUserName) {
        Bus bus = getById(busId);
        if(!bus.getBusOperator().getUser().getUsername().equals(loggedInUserName)){
            throw new InvalidOwnershipException("You do not own this bus...");
        }
        scheduleRepository.deleteByBusId(busId);
        busRepository.delete(bus);
    }

    public void softDelete(int id) {
        Bus bus = getById(id);
        bus.setActive(false);
        busRepository.save(bus);
    }

    public List<BusType> getTypes() {
        return Arrays.stream(BusType.values()).toList();
    }
}
