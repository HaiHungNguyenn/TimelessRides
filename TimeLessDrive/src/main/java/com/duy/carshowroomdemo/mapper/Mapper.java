package com.duy.carshowroomdemo.mapper;

import org.modelmapper.ModelMapper;

public interface Mapper<E, D> {
    ModelMapper modelMapper = new ModelMapper();
    E toEntity(D source);
    D toDto(E source);
}
