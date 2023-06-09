package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Car;

import com.duy.carshowroomdemo.entity.CarDescription;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.CarDescriptionRepository;
import com.duy.carshowroomdemo.repository.CarRepository;
import com.duy.carshowroomdemo.util.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();
    private MapperManager mapperManager = new MapperManager();

    public List<CarDto> getCarList(){
        List<CarDto> carList = new ArrayList<>();
        repository.findAll().forEach(x -> {carList.add(mapperManager.getCarMapper().toDto(x));});
        return carList;
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

    public CarDto findCarById(String id) {
        return mapperManager.getCarMapper().toDto(repository.findById(id).get());
    }

    public List<CarDto> getCarPerPage(Pageable pageable){
        List<CarDto> carDtoList = new ArrayList<>();
        repository.findAllByStatus(Status.AVAILABLE,pageable)
                .forEach((x)-> carDtoList.add(mapperManager.getCarMapper().toDto(x)));
        return carDtoList;
    }


    public List<CarDto> getCarSortedPerPage(Pageable pageable) {
        List<CarDto> carDtoList = new ArrayList<>();
        repository.findAllByStatus(Status.AVAILABLE,pageable).forEach(x ->{
            carDtoList.add(mapperManager.getCarMapper().toDto(x));

        });
        return carDtoList;

    }


    public long getLastOffset(int size) {
        return repository.findAllByStatus(Status.AVAILABLE,PageRequest.of(0,size)).getTotalPages();
    }





}
