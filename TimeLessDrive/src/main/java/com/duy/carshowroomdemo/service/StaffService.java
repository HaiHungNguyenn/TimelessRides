package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.StaffRepository;
import com.duy.carshowroomdemo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository repository;
    private MapperManager mapperManager = new MapperManager();

    public void save(StaffDto staff){
        repository.save(mapperManager.getStaffMapper().toEntity(staff));
    }

    public StaffDto findById(String id) {
        return mapperManager.getStaffMapper().toDto(repository.findById(id).orElse(null));
    }

    public StaffDto login(String email, String pass){
//        return mapperManager.getStaffMapper().toDto(repository.findAll().get(0));
        Optional<Staff> staff = repository.findByEmail(email);
        if(staff.isEmpty()){
            return null;
        }else {
            String encodedPW = staff.get().getPassword();
            return Util.isValidPW(pass, encodedPW) ? mapperManager.getStaffMapper().toDto(staff.get()) : null;
        }
    }

    public boolean isExist(String email) {
        return repository.existsByEmail(email);
    }

    public boolean changePassword(String id, String oldPass, String newPass) {
        Staff staff = repository.findById(id).get();
        try {
            if(Util.isValidPW(oldPass,staff.getPassword())){
                staff.setPassword(newPass);
                repository.save(staff);
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
}
