package com.fsx.repository;

import com.fsx.dto.PassengerSeatDto;
import com.fsx.enums.BookingStatus;
import com.fsx.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByBookingId(int id);

    @Query("""
            select t.seatNumber from Ticket t
            where t.booking.schedule.id =:scheduleId
            and (t.booking.bookingStatus = 'CONFIRMED' OR t.booking.bookingStatus = 'PENDING')""")
    List<Integer> getBookedSeats(int scheduleId);


    @Query("""
            select (count(t.id)) FROM Ticket t 
            where t.booking.schedule.id = :scheduleId 
            and t.seatNumber in :numbers 
            and t.booking.bookingStatus in :activeStatuses""")
    int countReservedSeats(@Param("scheduleId") int scheduleId, @Param("numbers")List<Integer> numbers, @Param("activeStatuses")List<BookingStatus> activeStatuses);
}
