package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.OffMeeting;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link OffMeeting}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffMeetingDto implements Serializable {
    private String id;
    private ClientDto client;
    private StaffDto staff;
    private LocalDate meetingDate;
    private LocalTime meetingTime;
    private LocalDate createDate;
    private LocalTime createTime;
    private String description;
    private String status;

    public String toString(){
        return client.getName() + staff.getName() + meetingDate + description + getStatus();
    }
}