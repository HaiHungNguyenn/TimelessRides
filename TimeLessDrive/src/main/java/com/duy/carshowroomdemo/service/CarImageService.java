package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarImageDto;
import com.duy.carshowroomdemo.entity.CarImage;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.CarImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarImageService {
    @Autowired
    private CarImageRepository carImageRepository;
    private MapperManager mapperManager = new MapperManager();

    public CarImageDto findById(String id) {
        Optional<CarImage> image = carImageRepository.findById(id);
        return mapperManager.getCarImageMapper().toDto(image.orElse(null));
    }
}
