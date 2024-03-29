package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDescriptionDto;
import com.duy.carshowroomdemo.dto.CarImageDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.util.Util;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Car}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDto implements Serializable {
    private String id;
    private String name;
    private Long price;
    private String status;
    private CarDescriptionDto carDescription;
    private List<CarImageDto> carImageList;
//    private PostDto post;


    public String getFormattedPrice() {
        return Util.formatUSDPrice(price);
    }

    public List<CarImageDto> getCarImageList() {
        return (carImageList != null) ? carImageList:new ArrayList<>();
    }
}