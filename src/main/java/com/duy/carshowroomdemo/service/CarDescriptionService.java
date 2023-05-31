package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.repository.CarDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDescriptionService {
    @Autowired
    private CarDescriptionRepository carDescriptionRepository;
}
