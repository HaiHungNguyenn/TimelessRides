package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.List;

public interface CarDescriptionRepository extends JpaRepository<CarDescription, String>{

    List<CarDescription> findByMake(String make);


    Page<CarDescription> findCarDescriptionsByMake(String value, Pageable pageable);

    Page<CarDescription> findCarDescriptionsByModel(String value, Pageable pageable);

    Page<CarDescription> findCarDescriptionsByBody(String value, Pageable pageable);

}