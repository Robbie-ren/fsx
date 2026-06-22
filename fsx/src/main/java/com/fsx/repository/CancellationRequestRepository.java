package com.fsx.repository;

import com.fsx.model.CancellationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancellationRequestRepository extends JpaRepository<CancellationRequest, Integer> {

    @Query("""
            select c from CancellationRequest c where c.booking.schedule.bus.busOperator.user.username=?1 and c.status='PENDING'""")
    List<CancellationRequest> getAllPendingRequests(String username);

    CancellationRequest findByBookingId(int bookingId);
}
