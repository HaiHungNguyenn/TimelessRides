package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "role", length = 50)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "avatar", length = 100)
    private String avatar;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "gender", length = 50)
    private String gender;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "joined_at")
    private LocalDate joinDate;

    @OneToMany(mappedBy = "staff")
    private List<Invoice> invoiceList;

    @OneToMany(mappedBy = "staff")
    private List<OffMeeting> offMeetingList;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<StaffNotification> notificationList;
}