package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findByEmailAndPassword(String email, String pass);

}