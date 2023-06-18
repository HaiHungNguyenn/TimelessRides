package com.duy.carshowroomdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.duy.carshowroomdemo.entity.ClientNotification}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientNotificationDto implements Serializable {
    private String id;
    private ClientDto receiver;
    private String content;
    private LocalDate createDate;
    private LocalTime createTime;
}