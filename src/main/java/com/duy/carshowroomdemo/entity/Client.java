package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "avatar", length = 100)
    private String avatar;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "join_at")
    private LocalDate joinDate;

    @Column(name = "tax", length = 50)
    private String tax;

    @OneToMany(mappedBy = "client")
    private List<Post> postList;

    @OneToMany(mappedBy = "client")
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "client")
    private List<OffMeeting> offMeetingList;

    @OneToMany(mappedBy = "client")
    private List<Invoice> invoiceList;
}