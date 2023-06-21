package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {
    Optional<Staff> findByEmailAndPassword(String email, String pass);
    Optional<Staff> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteAllByEmail(String mail);
}