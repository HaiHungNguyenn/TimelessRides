package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Invoice;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Showroom;
import com.duy.carshowroomdemo.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * DTO for {@link Staff}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto implements Serializable {
    private String id;
    private String role;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private LocalDate dob;
    private LocalDate joinDate;
    private Showroom showroom;

    public String getWorkingTime(){
        StringBuilder result = new StringBuilder();
        Period workingTime = Period.between(this.getJoinDate(), LocalDate.now());
        int workingMonths = workingTime.getMonths();

        if (workingMonths >= 1){
            int workingYears = workingTime.getYears();

            if (workingYears >= 1){
                result.append(workingYears).append((workingYears == 1) ? "year, " : " years, ");
                workingMonths -= workingYears * 12;
            }

            if (workingMonths >= 1){
                result.append(workingMonths).append((workingMonths == 1) ? " month, " : " months, ");
            }
        }

        return result.append(workingTime.getDays()).append(" days").toString();
    }
}