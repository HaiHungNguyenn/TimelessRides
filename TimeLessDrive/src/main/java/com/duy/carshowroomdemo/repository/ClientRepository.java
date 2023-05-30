package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByEmailAndPassword(String email, String pass);

    Optional<Client> findByEmail(String email);
    List<Client> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);




    boolean existsByEmail(String email);

}