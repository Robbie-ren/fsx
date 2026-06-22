package com.fsx.model;

import com.fsx.enums.PassengerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String gender;

    private int age;

    private String email;

    private String phoneNumber;

    @OneToOne
    private User user;

    private String idPath;

    @Enumerated(EnumType.STRING)
    private PassengerStatus status;
}
