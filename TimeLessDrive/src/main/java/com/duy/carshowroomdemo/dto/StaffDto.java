package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * DTO for {@link Staff}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto implements Serializable {
    String id;
    String role;
    String name;
    String avatar;
    String email;
    String phone;
    String gender;
    String address;
    LocalDate dob;
    LocalDate joinDate;
    public int getWorkingTime(){
        return Period.between(this.getJoinDate(), LocalDate.now()).getMonths();
    }
}