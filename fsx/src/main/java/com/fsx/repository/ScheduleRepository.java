package com.fsx.repository;

import com.fsx.dto.FilterReqDto;
import com.fsx.dto.ScheduleRespDto;
import com.fsx.model.Schedule;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>, ScheduleRepositoryCustom {


    @Transactional
    @Modifying
    @Query("""
            update Schedule s set s.availableSeats = s.availableSeats - :seatCount where s.id =:scheduleId""")
    void decrementAvailableSeats(int scheduleId, int seatCount);

    Page<Schedule> findByBusBusOperatorId(int busOperatorId, Pageable pageable);

    @Modifying
    void deleteByBusId(int busId);

    Page<Schedule> findByBusBusOperatorUserUsername(String username, Pageable pageable);

    @Query("""
                SELECT s
                FROM Schedule s
                WHERE (
                    LOWER(s.route.sourceCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(s.route.destinationCity) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(s.bus.busNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(s.bus.busName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
                AND s.active = :active
                AND s.bus.busOperator.user.username = :username
            """)
    Page<Schedule> searchSchedules(
            String keyword,
            boolean active,
            String username,
            Pageable pageable
    );
}
