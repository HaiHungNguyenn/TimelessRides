package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffRepository repository;
    private MapperManager mapperManager = new MapperManager();

    public void save(StaffDto staff){
        repository.save(mapperManager.getStaffMapper().toEntity(staff));
    }

    public StaffDto findById(int i) {
        return mapperManager.getStaffMapper().toDto(repository.findById(i).orElse(null));
    }

    public StaffDto login(String email, String pass){
        return mapperManager.getStaffMapper().toDto(repository.findByEmailAndPassword(email, pass).orElse(null));
    }

}
