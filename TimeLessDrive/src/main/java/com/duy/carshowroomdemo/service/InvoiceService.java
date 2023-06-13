package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.InvoiceDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Invoice;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.repository.InvoiceRepository;
import com.duy.carshowroomdemo.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public boolean createNewInvoice(ClientDto client, StaffDto staff, CarDto car, String additionalInfo) {
        Invoice invoice = new Invoice();
        invoice.setCar(modelMapper.map(car, Car.class));
        invoice.setClient(modelMapper.map(client, Client.class));
        invoice.setStaff(modelMapper.map(staff, Staff.class));
        invoice.setTotal(car.getPrice());
        invoice.setTax(Util.getRandText(10));
        invoice.setStatus("Paid");
        invoice.setCreateDate(LocalDate.now());
        invoice.setOtherInformation(additionalInfo);

        return (invoiceRepository.save(invoice) != null);
    }

    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public List<InvoiceDto> findByClient(Client client) {
        List<InvoiceDto> invoiceList = new ArrayList<>();
        invoiceRepository.findAllByClient(client).forEach((x) -> invoiceList.add(modelMapper.map(x, InvoiceDto.class)));
        return invoiceList;
    }
}
