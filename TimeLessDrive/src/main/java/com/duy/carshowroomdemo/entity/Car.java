package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "price")
    private Long price;

    @Column(name = "status", length = 50)
    private String status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CarDescription carDescription;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Showroom showroom;

    @OneToMany(mappedBy = "car")
    private List<OffMeeting> offMeetingList;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarImage> carImageList;

    @OneToMany(mappedBy = "car")
    private List<Invoice> invoiceList;

    @OneToOne(mappedBy = "car")
    private Post post;

    public List<CarImage> getCarImageList(){
        if (carImageList == null){
            carImageList = new ArrayList<>();
        }
        return carImageList;
    }
}