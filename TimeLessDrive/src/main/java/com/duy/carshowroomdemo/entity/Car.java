package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "price")
    private Long price;

    @Column(name = "status", length = 50)
    private String status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarDescription carDescription;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Showroom showroom;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarImage> carImageList;

    @OneToMany(mappedBy = "car")
    private List<Invoice> invoiceList;

    @OneToOne(mappedBy = "car")
    private Post post;
}