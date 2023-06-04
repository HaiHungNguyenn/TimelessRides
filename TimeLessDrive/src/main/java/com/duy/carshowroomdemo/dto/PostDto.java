package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Post;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link Post}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto implements Serializable {
    private String id;
    private CarDto car;
    private ClientDto client;
    private String description;
    private LocalDate postDate;
    private LocalTime postTime;
    private String status;
}