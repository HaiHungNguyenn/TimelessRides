package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    @Override
    Optional<Admin> findById(Integer integer);

    Optional<Admin> findByEmailAndPassword(String email, String password);
}