package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarImageRepository extends JpaRepository<CarImage, String>{
}