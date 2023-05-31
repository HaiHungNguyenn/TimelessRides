package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Showroom;
import org.springframework.data.repository.CrudRepository;

public interface ShowroomRepository extends CrudRepository<Showroom, Integer> {
}