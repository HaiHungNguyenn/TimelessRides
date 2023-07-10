package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Feedback;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Feedback}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackDto implements Serializable {
    private String id;
    private LocalDate createdAt;
    private String description;
    private double rating;

}