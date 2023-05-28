package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Feedback;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Feedback}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackDto implements Serializable {
    String id;
    LocalDate createdAt;
    String description;
}