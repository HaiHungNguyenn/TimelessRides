package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.CarImageDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.CarImage;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CarImageMapper implements Mapper<CarImage, CarImageDto> {
    @Override
    public CarImage toEntity(CarImageDto source){
        return (source == null) ? null : modelMapper.map(source, CarImage.class);
    }
    @Override
    public CarImageDto toDto(CarImage source){
        return (source == null) ? null : modelMapper.map(source, CarImageDto.class);
    }

}
