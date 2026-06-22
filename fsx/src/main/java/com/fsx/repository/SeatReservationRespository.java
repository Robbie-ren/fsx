package com.fsx.repository;

import com.fsx.model.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatReservationRespository extends JpaRepository<SeatReservation, Integer> {

    @Query("""
select s from SeatReservation s where s.booking.id=?1""")
    List<SeatReservation> getReservation(int bookingId);
}
