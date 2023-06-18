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
    private String id;
    private StaffDto receiver;
    private String content;
    private LocalDate createDate;
    private LocalTime createTime;
    private String status;

}