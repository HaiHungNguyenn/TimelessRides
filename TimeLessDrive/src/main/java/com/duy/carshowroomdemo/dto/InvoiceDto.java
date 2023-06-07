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
    private String id;
    private CarDto car;
    private Long total;
    private LocalDate createDate;
    private LocalTime createTime;
    private String status;
    private String tax;
    private String otherInformation;
}