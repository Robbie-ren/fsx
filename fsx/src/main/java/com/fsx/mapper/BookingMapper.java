package com.fsx.mapper;

import com.fsx.dto.*;
import com.fsx.enums.BookingStatus;
import com.fsx.model.Booking;
import com.fsx.model.Passenger;
import com.fsx.model.Schedule;
import com.fsx.service.PassengerService;
import com.fsx.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BookingMapper {


    public static Booking mapDtoToEntity(BookingReqDto dto, Schedule schedule, Passenger passenger){
        Booking booking = new Booking();
        booking.setSchedule(schedule);
        booking.setPassenger(passenger);
        booking.setSeatCount(dto.seatNumbers().size());
        return booking;
    }

    public static BookingDto mapEntityToDto(Booking booking){

        return new BookingDto(booking.getId(),
                booking.getPassenger().getName(),
                booking.getBookingDate(),
                booking.getSchedule().getRoute().getSourceCity(),
                booking.getSchedule().getRoute().getDestinationCity(),
                booking.getSchedule().getDepartureDate(),
                booking.getSchedule().getDepartureTime(),
                booking.getSchedule().getBus().getBusName(),
                booking.getSchedule().getBus().getBusNumber(),
                booking.getTotalAmount(),
                booking.getBookingStatus(),
                booking.getSeatCount()
        );


    }


}
