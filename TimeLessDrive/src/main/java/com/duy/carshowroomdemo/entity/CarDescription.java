package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "car_description")
public class CarDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "no_of_seat")
    private Short noOfSeat;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(name = "HP")
    private Short hp;

    @Column(name = "wheel_size")
    private Short wheelSize;

    @Column(name = "bought_year")
    private Short boughtYear;

    @Column(name = "width")
    private Short width;

    @Column(name = "length")
    private Short length;

    @Column(name = "height")
    private Short height;

    @Column(name = "km_spend", length = 50)
    private String kmSpend;

    @Column(name = "manufactured_year")
    private Short manufacturedYear;

    @Column(name = "others", length = 2000)
    private String others;

    @OneToOne(mappedBy = "carDescription")
    private Car car;
}