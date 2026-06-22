package com.fsx.repository;

import com.fsx.model.Bus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusRepository extends JpaRepository<Bus, Integer> {



    Page<Bus> findByBusOperatorUserUsernameAndActive(String loggedInUsername, boolean active, Pageable pageable);
}
