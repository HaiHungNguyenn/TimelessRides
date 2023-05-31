package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
}