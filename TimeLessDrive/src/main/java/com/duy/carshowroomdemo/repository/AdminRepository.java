package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    @Override
    Optional<Admin> findById(String id);

    Optional<Admin> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);
}