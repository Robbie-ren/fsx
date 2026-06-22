package com.fsx.repository;

import com.fsx.model.Booking;
import com.fsx.model.Bus;
import com.fsx.model.BusOperator;
import com.fsx.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BusOperatorRepository extends JpaRepository<BusOperator, Integer> {

    BusOperator findByUserUsername(String loggedInUserName);

    @Query("""
            select b from Bus b where b.busOperator.user.username=?1 and b.active=true""")
    List<Bus> getAllBusesByBusOperator(String username);

    @Query("""
            select s from Schedule s where s.bus.busOperator.user.username=?1 and s.active=true""")
    List<Schedule> getAllActiveSchedules(String username);


    @Query("""
            select b from Booking b where b.schedule.bus.busOperator.user.username=?1 and b.bookingStatus='CONFIRMED'""")
    List<Booking> getAllBookingsByOperator(String username);


    @Query("""
            select sum(b.totalAmount) from Booking b where b.schedule.bus.busOperator.user.username=?1 and b.bookingStatus='CONFIRMED'""")
    Double getTotalRevenue(String username);
}
