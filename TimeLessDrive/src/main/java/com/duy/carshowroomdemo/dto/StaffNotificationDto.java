package com.duy.carshowroomdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.StaffNotification}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffNotificationDto implements Serializable {
    String id;
    StaffDto receiver;
    String content;
    LocalDate createDate;
    LocalTime createTime;
}