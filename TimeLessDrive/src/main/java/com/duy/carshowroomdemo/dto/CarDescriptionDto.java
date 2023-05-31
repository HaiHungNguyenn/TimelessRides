package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.CarDescription;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link CarDescription}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDescriptionDto implements Serializable {
    String id;
    String color;

    Short noOfSeat;
    String fuelType;
    Short hp;
    Short wheelSize;
    Short boughtYear;
    Short width;
    Short length;
    Short height;
    String kmSpend;
    Short manufacturedYear;
    String others;
}