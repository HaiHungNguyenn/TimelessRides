package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.CarDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
//    Optional<Car> findCarByNameAndBrand(String carName, String brand);

    Car findByName(String carName);
     Optional<Car> findById(String carID);

     Page<Car> findAllByStatus(String status, Pageable pageable);



}