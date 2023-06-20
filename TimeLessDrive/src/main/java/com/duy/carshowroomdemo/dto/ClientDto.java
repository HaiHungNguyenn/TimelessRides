package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Client}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Cacheable("client")
public class ClientDto implements Serializable {
    private String id;
    private String role;
    private String name;
    private String avatar;
    private String phone;
    private String email;
    private String address;
    private String gender;
    private LocalDate dob;
    private LocalDate joinDate;

    public String getPhone() {
        return Util.formatPhone(phone);
    }
}