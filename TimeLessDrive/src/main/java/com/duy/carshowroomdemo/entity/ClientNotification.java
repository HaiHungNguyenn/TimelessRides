package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "client_notification")
public class ClientNotification {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @Column(name = "content", length = 2000)
    private String content;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_time")
    private LocalTime createTime;

}