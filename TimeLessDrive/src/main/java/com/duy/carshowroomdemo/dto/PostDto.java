package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Post;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDate expireDate;
    private LocalDate postDate;
    private LocalTime postTime;
    private int priority;
    private String plan;
    private String status;

    public String getPostTime() {
        return postTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}