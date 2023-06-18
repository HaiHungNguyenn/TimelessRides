package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.StaffNotification;
import com.duy.carshowroomdemo.repository.StaffNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffNotificationService {
    @Autowired
    StaffNotificationRepository staffNotificationRepository;

    public void save(StaffNotification notification) {
        staffNotificationRepository.save(notification);
    }
}
