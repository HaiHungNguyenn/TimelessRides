package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.OffMeeting;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link OffMeeting}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffMeetingDto implements Serializable {
    String id;
    ClientDto client;
    StaffDto staff;
    LocalDate meetingDate;
    LocalDate createAt;
    String description;
    String status;

    public String toString(){
        return client.getName() + staff.getName() + meetingDate + description + status;
    }
}