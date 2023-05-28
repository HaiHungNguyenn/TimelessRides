package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Client}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto implements Serializable {
    int id;
    String role;
    String name;
    String avatar;
    String phone;
    String email;
    String address;
    String gender;
    LocalDate dob;
    LocalDate joinDate;
    String tax;
}