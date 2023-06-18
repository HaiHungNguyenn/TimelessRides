package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.StaffNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffNotificationRepository extends JpaRepository<StaffNotification, String> {
}