package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.InvoiceDto;
import com.duy.carshowroomdemo.entity.Invoice;

public class InvoiceMapper implements Mapper<Invoice, InvoiceDto> {
    @Override
    public Invoice toEntity(InvoiceDto source) {
        return (source == null) ? null : modelMapper.map(source, Invoice.class);
    }

    @Override
    public InvoiceDto toDto(Invoice source) {
        return (source == null) ? null : modelMapper.map(source, InvoiceDto.class);
    }
}
