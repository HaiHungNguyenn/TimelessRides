package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Car;

import com.duy.carshowroomdemo.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository repository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<Car> loadCars(){
        return new ArrayList<>(repository.findAll());
    }

    public void paging(){
        Page<Car> all = repository.findAll(PageRequest.of(1, 10));

    }

//    public CarDto findCarByNameAndBrandAndBoughtYearAndLicensePlate(String carName, String brand, int boughtYear, String licensePlate) {
//
//        Optional<Car> car = repository.findCarByNameAndBrand(carName, brand);
//        if(car.isEmpty()){
//            return null;
//        }
//
//        if(car.get().getCarDescription().getBoughtYear() != boughtYear || !car.get().getCarDescription().getLicensePlate().equalsIgnoreCase(licensePlate)){
//            return null;
//        }
//
//        return modelMapper.map(car.get(), CarDto.class);
//    }

    public Car findCarByName(String carName) {
        return repository.findByName(carName);
    }

    public void save(Car car) {
        repository.save(car);
    }
}
