package com.duy.carshowroomdemo.dto;

import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.CarImage}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarImageDto implements Serializable {
    private String id;
    private byte[] content;

}