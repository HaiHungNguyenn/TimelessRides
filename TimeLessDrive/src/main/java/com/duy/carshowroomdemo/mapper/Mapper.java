package com.duy.carshowroomdemo.mapper;

import org.modelmapper.ModelMapper;

public interface Mapper<T, S> {
    ModelMapper modelMapper = new ModelMapper();
    T toEntity(S source);
    S toDto(T source);
}
