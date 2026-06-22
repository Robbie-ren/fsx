package com.fsx.repository;

import com.fsx.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    Passenger findByUserUsername(String username);
}
