package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.repository.CarImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarImageService {
    @Autowired
    private CarImageRepository carImageRepository;
}
