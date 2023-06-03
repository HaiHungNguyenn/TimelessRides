package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.Test;
import com.duy.carshowroomdemo.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepo imageRepo;

    public void save(Test image){
        imageRepo.save(image);
    }

    public List<Test> findAll() {
        return imageRepo.findAll();
    }
}
