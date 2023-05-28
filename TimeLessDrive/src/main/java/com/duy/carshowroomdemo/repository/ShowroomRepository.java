package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShowroomRepository extends JpaRepository<Showroom, String>{
}