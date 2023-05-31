package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.ShowroomDto;
import com.duy.carshowroomdemo.entity.Showroom;

public class ShowroomMapper implements Mapper<Showroom, ShowroomDto> {
    @Override
    public Showroom toEntity(ShowroomDto source) {
        return (source == null) ? null : modelMapper.map(source, Showroom.class);
    }

    @Override
    public ShowroomDto toDto(Showroom source) {
        return (source == null) ? null : modelMapper.map(source, ShowroomDto.class);
    }
}
