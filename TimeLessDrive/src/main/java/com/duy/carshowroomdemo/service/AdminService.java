package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;
    private MapperManager mapperManager = new MapperManager();

    public AdminDto login(String email, String pass){
        return mapperManager.getAdminMapper().toDto(repository.findByEmailAndPassword(email,pass).orElse(null));
    }

}
