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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "brand", length = 50)
    private String brand;

    @Column(name = "price")
    private Long price;

    @Column(name = "status", length = 20)
    private String status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarDescription carDescription;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Showroom showroom;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarImage> carImageList;

    @OneToMany(mappedBy = "car")
    private List<Invoice> invoiceList;

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    private Post post;
}