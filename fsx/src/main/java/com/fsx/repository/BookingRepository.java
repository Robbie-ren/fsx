package com.fsx.repository;

import com.fsx.dto.BookingDto;
import com.fsx.dto.BookingMonthStatJpqlRespDto;
import com.fsx.enums.BookingStatus;
import com.fsx.exception.InvalidBookingException;
import com.fsx.model.Booking;
import com.fsx.model.Passenger;
import com.fsx.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByBookingStatus(BookingStatus bookingStatus);


    Page<Booking> findByPassengerUserUsername(String loggedInUserName, Pageable pageable);

    @Query("""

            select new com.fsx.dto.BookingMonthStatJpqlRespDto(
           month(b.bookingDate) as month,
           count(b) as numberOfBookings
       )
       from Booking b
       where year(b.bookingDate) = year(current_date)
       group by month(b.bookingDate)
       order by month(b.bookingDate)
       """)
    List<BookingMonthStatJpqlRespDto> getNumberOfBookingsByMonth();

    Optional<Booking> findByPassengerAndScheduleAndBookingStatus(Passenger passenger, Schedule schedule, BookingStatus bookingStatus);

    @Query("""
            select b from Booking b where b.passenger.user.username=?1
            order by b.bookingDate desc""")
    Page<Booking> findByUsername(String loggedInUserName, Pageable pageable);


    @Query("""
    SELECT b
    FROM Booking b
    WHERE b.schedule.bus.busOperator.user.username = :username
    AND (
        LOWER(b.passenger.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(b.schedule.route.sourceCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(b.schedule.route.destinationCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
""")
    Page<Booking> searchBookings(String username, String keyword, Pageable pageable);


    @Query("""
    SELECT b
    FROM Booking b
    WHERE LOWER(b.passenger.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(b.schedule.route.sourceCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(b.schedule.route.destinationCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Booking> search(String keyword, Pageable pageable);

    //boolean existsByBooking(Booking booking);
}