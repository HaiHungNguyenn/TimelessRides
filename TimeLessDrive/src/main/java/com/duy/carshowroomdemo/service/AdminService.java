package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Admin;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.AdminRepository;
import com.duy.carshowroomdemo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;
    private final MapperManager mapperManager = MapperManager.getInstance();


    public AdminDto login(String email, String pass){
        Optional<Admin> admin = repository.findByEmail(email);
        if(admin.isEmpty()){
            return null;
        }else {
            String encodedPW = admin.get().getPassword();

            return Util.isValidPW(pass, encodedPW) ? mapperManager.getAdminMapper().toDto(admin.get())  : null;
        }
    }

    public boolean isExist(String email) {
        return repository.existsByEmail(email);
    }


    public AdminDto findByEmail(String email) {
        return mapperManager.getAdminMapper().toDto(repository.findByEmail(email).orElse(null));
    }
}
