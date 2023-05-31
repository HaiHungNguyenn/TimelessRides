package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarImage;
import org.springframework.data.repository.CrudRepository;

public interface CarImageRepository extends CrudRepository<CarImage, Integer> {
}