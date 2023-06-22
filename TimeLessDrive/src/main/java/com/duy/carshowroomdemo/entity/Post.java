package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "plan", length = 100)
    private String plan;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Column(name = "post_time")
    private LocalTime postTime;

    private String status;

    public List<String> getPlanList(){
        return Arrays.asList(plan.split(","));
    }

}