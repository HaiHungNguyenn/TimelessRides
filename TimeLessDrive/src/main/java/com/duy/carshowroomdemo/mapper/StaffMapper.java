package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Staff;

public class StaffMapper implements Mapper<Staff, StaffDto> {
    @Override
    public Staff toEntity(StaffDto source) {
        return (source == null) ? null : modelMapper.map(source, Staff.class);
    }

    @Override
    public StaffDto toDto(Staff source) {
        return (source == null) ? null : modelMapper.map(source, StaffDto.class);
    }
}
