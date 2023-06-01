package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository repository;
    public List<Car> loadCars(){
        List<Car> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }


}