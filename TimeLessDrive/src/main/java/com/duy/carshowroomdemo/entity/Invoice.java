package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "total")
    private Long total;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_time")
    private LocalTime createTime;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "tax", length = 100)
    private String tax;

    @Column(name = "others")
    private String otherInformation;

}