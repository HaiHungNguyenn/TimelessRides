package com.duy.carshowroomdemo;
import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.duy.carshowroomdemo.repository.*;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserFeatureTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    public void loadCar(){
        List<CarDto> carList = new ArrayList<>();
        carRepository.findAll().forEach(x -> {
            System.out.println(x.getName());
        });

    }
}
