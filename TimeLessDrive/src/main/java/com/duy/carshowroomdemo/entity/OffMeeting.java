package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "off_meeting")
public class OffMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "status", length = 20)
    private String status;

}