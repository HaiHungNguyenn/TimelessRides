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

    private int seats;

    @Column(name = "power")
    private int power;

    @Column(name = "engine_capacity")
    private int engineCapacity;

    @Column(name = "co2_emission")
    private int co2Emission;

    @Column(name = "kms_driven")
    private int kmsDriven;

    @Column(name = "others", length = 2000)
    private String others;

    @OneToOne(mappedBy = "carDescription")
    private Car car;
}