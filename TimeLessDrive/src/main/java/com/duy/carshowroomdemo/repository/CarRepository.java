package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Integer> {
}