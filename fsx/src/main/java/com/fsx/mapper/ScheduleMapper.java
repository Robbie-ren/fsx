package com.fsx.mapper;

import com.fsx.dto.ScheduleOperatorRespDto;
import com.fsx.dto.ScheduleReqDto;
import com.fsx.dto.ScheduleRespDto;
import com.fsx.model.Bus;
import com.fsx.model.Route;
import com.fsx.model.Schedule;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleRespDto mapToDto(Schedule schedule){
        return new ScheduleRespDto(
                schedule.getId(),
                schedule.getBus().getBusOperator().getCompanyName(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getBus().getTotalSeats(),
                schedule.getAvailableSeats(),
                schedule.getPrice(),
                schedule.getBus().getBusType(),
                schedule.getRoute().getDistanceInKm());
    }

    public Schedule mapToEntity(ScheduleReqDto dto, Bus bus, Route route){
        Schedule schedule = new Schedule();
        schedule.setDepartureDate(dto.departureDate());
        schedule.setDepartureTime(dto.departureTime());
        schedule.setArrivalTime(dto.arrivalTime());
        schedule.setPrice(dto.price());
        schedule.setBus(bus);
        schedule.setRoute(route);
        schedule.setAvailableSeats(bus.getTotalSeats());
        return schedule;
    }

    public ScheduleOperatorRespDto mapToRespDto(Schedule schedule){
        return new ScheduleOperatorRespDto(schedule.getId(),
                schedule.getDepartureDate(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getAvailableSeats(),
                schedule.getPrice(),
                schedule.getBus().getId(),
                schedule.getBus().getBusNumber(),
                schedule.getBus().getBusName(),
                schedule.getRoute().getSourceCity(),
                schedule.getRoute().getDestinationCity(),
                schedule.isActive());
    }
}
