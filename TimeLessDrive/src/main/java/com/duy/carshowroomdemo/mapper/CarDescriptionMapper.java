package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.CarDescriptionDto;
import com.duy.carshowroomdemo.entity.CarDescription;

public class CarDescriptionMapper implements Mapper<CarDescription, CarDescriptionDto> {

    @Override
    public CarDescription toEntity(CarDescriptionDto source) {
        return (source == null) ? null : modelMapper.map(source, CarDescription.class);
    }

    @Override
    public CarDescriptionDto toDto(CarDescription source) {
        return (source == null) ? null : modelMapper.map(source, CarDescriptionDto.class);
    }
}
