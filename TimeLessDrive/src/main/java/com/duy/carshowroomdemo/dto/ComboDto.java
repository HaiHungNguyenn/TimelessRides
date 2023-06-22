package com.duy.carshowroomdemo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.Combo}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComboDto implements Serializable {
    String id;
    String name;
    Long price;
    Integer duration;
}