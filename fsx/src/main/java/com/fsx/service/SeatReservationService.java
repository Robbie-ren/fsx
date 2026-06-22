package com.fsx.service;

import com.fsx.dto.PassengerSeatDto;
import com.fsx.exception.InvalidSeatException;
import com.fsx.model.Booking;
import com.fsx.model.SeatReservation;
import com.fsx.model.Ticket;
import com.fsx.repository.SeatReservationRespository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatReservationService {


    private final SeatReservationRespository seatReservationRespository;

    public SeatReservationService(SeatReservationRespository seatReservationRespository) {
        this.seatReservationRespository = seatReservationRespository;
    }

    public void addReservation(Booking booking, List<PassengerSeatDto> seatNumbers) {
        if (seatNumbers == null || seatNumbers.isEmpty()) {
            throw new InvalidSeatException("Seat must be chosen...");
        }
        List< SeatReservation> list = seatNumbers.stream().map(seat -> {
            SeatReservation reservation = new SeatReservation();
            reservation.setBooking(booking);
            reservation.setSeatNumber(seat.seatNumber());
            reservation.setSchedule(booking.getSchedule());
            reservation.setPassengerName(seat.passengerName());
            reservation.setPassengerAge(seat.passengerAge());
            return reservation;
        }).toList();
        seatReservationRespository.saveAll(list);
    }


    public List<SeatReservation> getReservation(int bookingId) {
        return seatReservationRespository.getReservation(bookingId);
    }
}
