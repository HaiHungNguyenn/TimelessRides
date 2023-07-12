package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.ClientNotificationDto;
import com.duy.carshowroomdemo.entity.ClientNotification;

public class ClientNotificationMapper implements Mapper<ClientNotification, ClientNotificationDto> {
    @Override
    public ClientNotification toEntity(ClientNotificationDto source) {
        return modelMapper.map(source, ClientNotification.class);
    }

    @Override
    public ClientNotificationDto toDto(ClientNotification source) {
        return modelMapper.map(source, ClientNotificationDto.class);
    }
}
