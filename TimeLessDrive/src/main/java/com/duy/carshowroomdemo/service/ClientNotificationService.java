package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.ClientNotification;
import com.duy.carshowroomdemo.repository.ClientNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientNotificationService {
    @Autowired
    ClientNotificationRepository clientNotificationRepository;

    public void save(ClientNotification notification) {
        clientNotificationRepository.save(notification);
    }
}
