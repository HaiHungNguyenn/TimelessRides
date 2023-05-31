package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Car;

public class CarMapper implements Mapper<Car, CarDto>{
    @Override
    public Car toEntity(CarDto source) {
        return (source == null) ? null : modelMapper.map(source, Car.class);
    }

    @Override
    public CarDto toDto(Car source) {
        return (source == null) ? null : modelMapper.map(source, CarDto.class);
    }
}
