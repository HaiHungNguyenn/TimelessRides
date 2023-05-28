package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.CarDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarDescriptionRepository extends JpaRepository<CarDescription, String>{
}