package com.duy.carshowroomdemo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.Admin}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDto implements Serializable {
    int id;
    String role;
    String name;
    String avatar;
    String email;
}