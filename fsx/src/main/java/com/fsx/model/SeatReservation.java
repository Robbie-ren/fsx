package com.fsx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class SeatReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Schedule schedule;

    private Integer seatNumber;

    private String passengerName;

    private int passengerAge;

    @CreationTimestamp
    private Instant reservedAt;
}
