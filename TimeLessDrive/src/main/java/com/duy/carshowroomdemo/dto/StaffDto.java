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
@Cacheable("staff")
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

    public int getWorkingTime(){
        return Period.between(this.getJoinDate(), LocalDate.now()).getMonths();
    }
}