package com.duy.carshowroomdemo.dto;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Invoice;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Invoice}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceDto implements Serializable {
    int id;
    CarDto car;
    Long total;
    LocalDate createdAt;
    String status;
}