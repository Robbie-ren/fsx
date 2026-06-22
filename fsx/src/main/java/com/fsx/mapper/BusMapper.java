package com.fsx.mapper;

import com.fsx.dto.BusReqDto;
import com.fsx.dto.BusResponseDto;
import com.fsx.model.Bus;
import com.fsx.model.BusOperator;
import org.springframework.stereotype.Component;

@Component
public class BusMapper {

    public Bus mapToEntity(BusReqDto dto, BusOperator busOperator){
        Bus bus = new Bus();
        bus.setBusName(dto.busName());
        bus.setBusNumber(dto.busNumber());
        bus.setBusType(dto.busType());
        bus.setTotalSeats(dto.totalSeats());
        bus.setBusOperator(busOperator);
        return bus;
    }

    public BusResponseDto mapToDto(Bus bus){
        return new BusResponseDto(
                bus.getId(),
                bus.getBusName(),
                bus.getBusNumber(),
                bus.getBusType(),
                bus.getTotalSeats(),
                bus.isActive());
    }
}
