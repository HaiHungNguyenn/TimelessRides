package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.ClientNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, String> {
}