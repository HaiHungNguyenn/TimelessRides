package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.ClientNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientNotificationRepository extends JpaRepository<ClientNotification, String> {
    List<ClientNotification> findAllByReceiver(Client receiver, Pageable pageable);
}