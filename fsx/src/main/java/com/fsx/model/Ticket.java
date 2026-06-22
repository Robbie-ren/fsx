package com.fsx.model;

import com.fsx.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String passengerName;

    private int passengerAge;

    @ManyToOne
    private Booking booking;

    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
