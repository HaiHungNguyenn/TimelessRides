package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    private MapperManager mapperManager = new MapperManager();

    public ClientDto findById(String id) {
        return mapperManager.getClientMapper().toDto(repository.findById(id).orElse(null));
    }

    public ClientDto login(String email, String pass){
            return mapperManager.getClientMapper().toDto(repository.findByEmailAndPassword(email,pass).orElse(null));
    }
}
