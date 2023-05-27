package com.duy.carshowroomdemo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.CarImage}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarImageDto implements Serializable {
    String id;
    String link;
}