package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "showroom")
public class Showroom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "phone", length = 10)
    private String phone;

    @OneToMany(mappedBy = "showroom")
    private List<Car> carList;

    @OneToMany(mappedBy = "showroom")
    private List<Staff> staffList;

}