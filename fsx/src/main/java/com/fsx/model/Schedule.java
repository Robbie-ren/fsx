package com.fsx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private int availableSeats;

    private double price;

    @ManyToOne
    private Bus bus;

    @ManyToOne
    private Route route;

    private boolean active;

}
