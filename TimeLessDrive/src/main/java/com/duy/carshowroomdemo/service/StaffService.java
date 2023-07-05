package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.StaffRepository;
import com.duy.carshowroomdemo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository repository;
    private final MapperManager mapperManager = MapperManager.getInstance();

    public void save(StaffDto staff){
        repository.save(mapperManager.getStaffMapper().toEntity(staff));
    }
    public void save(Staff staff) {repository.save(staff);}

    public StaffDto findById(String id) {
        return mapperManager.getStaffMapper().toDto(repository.findById(id).orElse(null));
    }

    public StaffDto login(String email, String pass){
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
        Staff staff = repository.findById(id).orElse(null);
        if (staff == null){
            return false;
        }

        if(Util.isValidPW(oldPass,staff.getPassword())) {
            staff.setPassword(Util.encodePassword(newPass));
            repository.save(staff);
            return true;
        }

        return false;
    }

    public StaffDto findByEmail(String email) {
        return mapperManager.getStaffMapper().toDto(repository.findByEmail(email).orElse(null));
    }


    public List<StaffDto> findAll(Pageable pageable) {
        List<StaffDto> list = new ArrayList<>();
        repository.findAll(pageable).forEach(x -> list.add(mapperManager.getStaffMapper().toDto(x)));
        return list;
    }

    public long getLastOffset(Pageable pageable){
        return repository.findAll(pageable).getTotalPages();
    }


    public Staff findEntityByEmail(String userEmail) {
        return repository.findByEmail(userEmail).orElse(null);
    }

    public void delete(StaffDto staff){
        repository.delete(mapperManager.getStaffMapper().toEntity(staff));
    }
}
