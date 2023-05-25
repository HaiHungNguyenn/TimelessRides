package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
}