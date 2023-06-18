package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "staff_notification")
public class StaffNotification {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Staff receiver;

    @Column(name = "content", length = 2000)
    private String content;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_time")
    private LocalTime createTime;

}