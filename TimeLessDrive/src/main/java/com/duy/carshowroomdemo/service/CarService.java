package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.Car;

import com.duy.carshowroomdemo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository repository;

    public List<Car> loadCars(){
        return new ArrayList<>(repository.findAll());
    }

    public void paging(){
        Page<Car> all = repository.findAll(PageRequest.of(1, 10));

    }
}
