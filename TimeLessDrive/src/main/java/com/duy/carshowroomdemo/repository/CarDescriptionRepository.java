package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarDescription;
import org.springframework.data.repository.CrudRepository;

public interface CarDescriptionRepository extends CrudRepository<CarDescription, Integer> {
}