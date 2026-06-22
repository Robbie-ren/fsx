package com.fsx.model;

import com.fsx.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant bookingDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private int seatCount;

    private double totalAmount;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Schedule schedule;
}
