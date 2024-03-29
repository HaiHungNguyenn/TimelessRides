package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "off_meeting")
public class OffMeeting {
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

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "meeting_time")
    private LocalTime meetingTime;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_time")
    private LocalTime createTime;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "status", length = 50)
    private String status;

}