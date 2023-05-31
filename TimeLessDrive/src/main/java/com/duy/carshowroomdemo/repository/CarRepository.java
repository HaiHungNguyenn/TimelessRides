package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, String> {
    Optional<Car> findCarByNameAndBrand(String carName, String brand);

}