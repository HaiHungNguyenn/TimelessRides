package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientNotificationDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.ClientNotification;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.ClientNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientNotificationService {
    @Autowired
    ClientNotificationRepository clientNotificationRepository;
    MapperManager mapperManager = MapperManager.getInstance();

    public void save(ClientNotification notification) {
        clientNotificationRepository.save(notification);
    }

    public List<ClientNotificationDto> findNotificationsByClient(Client client) {
        List<ClientNotificationDto> list = new ArrayList<>();

        clientNotificationRepository.findByReceiver(client)
                .forEach((x) -> list.add(mapperManager.getClientNotificationMapper().toDto(x)));

        return list;
    }
}
