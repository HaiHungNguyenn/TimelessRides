package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.ClientRepository;
import com.duy.carshowroomdemo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    private MapperManager mapperManager = new MapperManager();

    public ClientDto findById(String id) {
        return mapperManager.getClientMapper().toDto(repository.findById(id).orElse(null));
    }

//    public ClientDto login(String email, String pass){
//            return mapperManager.getClientMapper().toDto(repository.findByEmailAndPassword(email,pass).orElse(null));
//    }

    public ClientDto login(String email, String pass){
//        return mapperManager.getStaffMapper().toDto(repository.findAll().get(0));
        Optional<Client> client = repository.findByEmail(email);
        if(client.isEmpty()){
            return null;
        }else {
            String encodedPW = client.get().getPassword();
            return Util.isValidPW(pass, encodedPW) ? mapperManager.getClientMapper().toDto(client.get()) : null;
        }
    }
}
