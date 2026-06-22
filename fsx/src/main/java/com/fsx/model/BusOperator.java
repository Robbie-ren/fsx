package com.fsx.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;

    private String ownerName;

    private String email;

    private String phoneNumber;

    private String officeAddress;

    @OneToOne
    private User user;
}
