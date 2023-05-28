package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    Optional<Staff> findByEmailAndPassword(String email, String pass);
}