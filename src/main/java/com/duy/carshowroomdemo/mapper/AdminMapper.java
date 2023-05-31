package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.entity.Admin;

public class AdminMapper implements Mapper<Admin, AdminDto> {
    @Override
    public Admin toEntity(AdminDto source) {
        return (source == null) ? null : modelMapper.map(source, Admin.class);
    }

    @Override
    public AdminDto toDto(Admin source) {
        return (source == null) ? null : modelMapper.map(source, AdminDto.class);
    }
}
