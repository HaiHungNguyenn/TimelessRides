package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}