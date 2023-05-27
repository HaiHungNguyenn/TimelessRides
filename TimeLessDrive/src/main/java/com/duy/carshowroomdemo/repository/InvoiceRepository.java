package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}