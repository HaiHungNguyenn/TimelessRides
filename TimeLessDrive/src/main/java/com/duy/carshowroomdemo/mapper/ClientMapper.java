package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;

public class ClientMapper implements Mapper<Client, ClientDto> {
    @Override
    public Client toEntity(ClientDto source) {
        return (source == null) ? null : modelMapper.map(source, Client.class);
    }

    @Override
    public ClientDto toDto(Client source) {
        return (source == null) ? null : modelMapper.map(source, ClientDto.class);
    }
}
