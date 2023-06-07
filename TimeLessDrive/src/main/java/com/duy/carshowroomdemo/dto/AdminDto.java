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
    private String id;
    private String role;
    private String name;
    private String avatar;
    private String email;
}