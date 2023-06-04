package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Invoice;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link Invoice}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceDto implements Serializable {
    String id;
    CarDto car;
    Long total;
    LocalDate createDate;
    LocalTime createTime;
    String status;
    String tax;
    String otherInformation;
}