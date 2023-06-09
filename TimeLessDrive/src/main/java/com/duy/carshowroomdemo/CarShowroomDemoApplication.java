package com.duy.carshowroomdemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class CarShowroomDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarShowroomDemoApplication.class, args);
    }
}
