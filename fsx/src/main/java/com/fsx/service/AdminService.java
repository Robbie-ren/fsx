package com.fsx.service;

import com.fsx.dto.CombinedStatAdminDto;
import com.fsx.dto.CombinedStatBusOperatorDto;
import com.fsx.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    public final PassengerService passengerService;
    public final BusOperatorService busOperatorService;
    public final ScheduleService scheduleService;
    public final BookingService bookingService;

    public CombinedStatAdminDto getCombinedStat() {

        List<Passenger> passengers = passengerService.getPassengers();
        List<BusOperator> operators = busOperatorService.getOperators();
        List<Schedule> activeSchedules = scheduleService.getSchedules();
        List<Booking> bookings = bookingService.getBookings();


        List<String> label = List.of("Total Passengers", "Bus Operators", "Schedules", "Total Bookings");
        List<Long> count = List.of((long)passengers.size(), (long)operators.size(), (long)activeSchedules.size(),(long)bookings.size());
        return new CombinedStatAdminDto(label, count);
    }
}
