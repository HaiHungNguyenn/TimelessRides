package com.duy.carshowroomdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "image_name", length = 2000)
    private String imageName;

    @Lob
    @Column(name = "content", length = Integer.MAX_VALUE)
    private byte[] content;

}