package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByEmailAndPassword(String email, String pass);

    @Override
    Optional<Client> findById(String s);


    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Client> findByJoinDateBetween(LocalDate startDate, LocalDate endDate);

    Client findByEmailAndNameAndPhone(String email, String name, String phone);

    Page<Client> findClientsByName(Pageable pageable, String name);

    void deleteByEmail(String s);
}