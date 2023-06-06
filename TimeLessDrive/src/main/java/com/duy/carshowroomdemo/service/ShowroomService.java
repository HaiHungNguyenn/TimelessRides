package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.Showroom;
import com.duy.carshowroomdemo.repository.ShowroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowroomService {
    @Autowired
    private ShowroomRepository showroomRepository;

    public List<Showroom> findAll() {
        return showroomRepository.findAll();
    }
}
