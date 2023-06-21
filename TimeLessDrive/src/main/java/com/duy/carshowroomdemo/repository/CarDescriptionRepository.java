package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface CarDescriptionRepository extends JpaRepository<CarDescription, String>{

    List<CarDescription> findByMake(String make);


    Page<CarDescription> findCarDescriptionsByMake(String value, Pageable pageable);

    Page<CarDescription> findCarDescriptionsByModel(String value, Pageable pageable);

    Page<CarDescription> findCarDescriptionsByBody(String value, Pageable pageable);

    @Query("select make from CarDescription group by make")
    List<String> findAllMakes();

    @Query("select model from CarDescription group by model")
    List<String> findAllModels();

    @Query("select body from CarDescription group by body")
    List<String> findAllBodies();

    @Query("select transmission from CarDescription group by transmission")
    List<String> findAllTrans();

    @Query("select fuelType from CarDescription group by fuelType")
    List<String> findAllFuels();
}