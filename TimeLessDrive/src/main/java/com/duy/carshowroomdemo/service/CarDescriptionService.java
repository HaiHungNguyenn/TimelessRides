package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.CarDescription;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.CarDescriptionRepository;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarDescriptionService {
    @Autowired
    private CarDescriptionRepository carDescriptionRepository;
    private final MapperManager mapperManager = MapperManager.getInstance();


    public List<CarDto> findCarByMake(String value, Pageable pageable) {

        List<CarDto> carList = new ArrayList<>();


       carDescriptionRepository.findCarDescriptionsByMake(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
//        repository.findAllByStatus(Status.AVAILABLE,pageable)
//                .forEach((x)-> carDtoList.add(mapperManager.getCarMapper().toDto(x)));



        return carList;

    }

//    public List<CarDto> findCarByModel(String value) {
//        List<com.duy.carshowroomdemo.dto.CarDto> carList = new ArrayList<>();
//
//        List<CarDescription> carDescriptions = new ArrayList<>();
//
//        carDescriptions =  carDescriptionRepository.findCarDescriptionsByModel(value);
//
//        carDescriptions.forEach(x ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
//
//        return carList;
//    }
//
//    public List<CarDto> findCarByBodyStyle(String value) {
//        List<CarDto> carList = new ArrayList<>();
//
//        List<CarDescription> carDescriptions = new ArrayList<>();
//
//        carDescriptions =  carDescriptionRepository.findCarDescriptionsByBody(value);
//
//        carDescriptions.forEach(x ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
//
//        return carList;
//    }


    public List<CarDto> getSearchedCarByMakePerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByMake(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }
    public List<CarDto> getSearchedCarByMakeSortedPerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByMake(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }
    public List<CarDto> getSearchedCarByModelPerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByModel(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }
    public List<CarDto> getSearchedCarByModelSortedPerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByModel(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }
    public List<CarDto> getSearchedCarByBodyPerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByBody(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }
    public List<CarDto> getSearchedCarByBodySortedPerPage(String value,Pageable pageable) {
        List<CarDto> carList = new ArrayList<>();
        carDescriptionRepository.findCarDescriptionsByBody(value,pageable).forEach((x) ->{ carList.add(mapperManager.getCarMapper().toDto(x.getCar()));});
        return carList;
    }

    public long getLastOffset(String value,String field,int size) {
//        return repository.findAllByStatus(Status.AVAILABLE,PageRequest.of(0,size)).getTotalPages();
        switch (field){
            case"make":
                return carDescriptionRepository.findCarDescriptionsByMake(value ,PageRequest.of(0,size)).getTotalPages();
            case "model":
                return carDescriptionRepository.findCarDescriptionsByModel(value ,PageRequest.of(0,size)).getTotalPages();
            case "body":
                return carDescriptionRepository.findCarDescriptionsByBody(value ,PageRequest.of(0,size)).getTotalPages();
            default:
                return 0;
        }


    }


}
