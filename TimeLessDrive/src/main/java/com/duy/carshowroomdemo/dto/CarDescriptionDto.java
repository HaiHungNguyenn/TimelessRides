package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.CarDescription;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.Optional;

/**
 * DTO for {@link CarDescription}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDescriptionDto implements Serializable {
    private String id;
    private String make;
    private String model;
    private String bodyColor;
    private String interiorColor;
    private String interiorMaterial;
    private String body;
    private String licensePlate;
    private String fuelType;
    private String transmission;
    private String firstRegistration;
    private int seats;
    private String power;
    private String engineCapacity;
    private String co2Emission;
    private String mileage;
    private String others;

    public String getLicensePlate() {
        if (licensePlate == null || licensePlate.isEmpty()){
            return "";
        }
        return licensePlate.substring(0, 3) + "-" + licensePlate.substring(3, 6) + "." + licensePlate.substring(6);
    }
}