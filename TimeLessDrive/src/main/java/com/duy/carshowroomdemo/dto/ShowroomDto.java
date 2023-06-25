package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Showroom;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;

/**
 * DTO for {@link Showroom}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowroomDto implements Serializable {
    private String id;
    private String name;
    private String address;
    private String city;
    private String phone;
}