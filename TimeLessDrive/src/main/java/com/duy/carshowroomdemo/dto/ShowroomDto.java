package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Showroom;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Showroom}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowroomDto implements Serializable {
    int id;
    String name;
    String address;
    String city;
    String phone;
}