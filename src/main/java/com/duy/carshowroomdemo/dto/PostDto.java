package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Post;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Post}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto implements Serializable {
    int id;
    CarDto car;
    ClientDto client;
    String description;
    LocalDate createdAt;
}