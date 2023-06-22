package com.duy.carshowroomdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "combo")
public class Combo {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "duration")
    private Integer duration;
}