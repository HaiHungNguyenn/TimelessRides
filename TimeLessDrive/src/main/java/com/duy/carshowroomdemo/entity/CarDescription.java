package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "car_description")
public class CarDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    private String make;

    private String model;

    @Column(name = "body_color", length = 50)
    private String bodyColor;

    @Column(name = "interior_color", length = 50)
    private String interiorColor;

    @Column(name = "interior_material", length = 50)
    private String interiorMaterial;

    private String body;

    @Column(name = "license_plate", length = 50)
    private String licensePlate;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    private String transmission;

    @Column(name = "first_registration")
    private String firstRegistration;

    @Column(name = "seats")
    private int seats;

    @Column(name = "power", length = 100)
    private String power;

    @Column(name = "engine_capacity", length = 100)
    private String engineCapacity;

    @Column(name = "co2_emission", length = 100)
    private String co2Emission;

    @Column(name = "mileage")
    private String mileage;

    @Column(name = "others", length = 2000)
    private String others;

    @OneToOne(mappedBy = "carDescription")
    private Car car;
}