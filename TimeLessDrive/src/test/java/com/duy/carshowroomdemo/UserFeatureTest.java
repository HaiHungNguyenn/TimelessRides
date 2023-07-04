package com.duy.carshowroomdemo;
import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import com.fasterxml.jackson.databind.DatabindContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.duy.carshowroomdemo.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserFeatureTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OffMeetingRepository offMeetingRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    public void loadCar(){
        List<CarDto> carList = new ArrayList<>();
        carRepository.findAll().forEach(x -> {
            System.out.println(x.getName());
        });

    }
    @Test
    public void addMeeting(){
        OffMeeting offMeeting = new OffMeeting();
        offMeeting.setClient(clientRepository.findById("0cd3ffd2-b558-4450-aaf4-2d73a2e494a3").get());
        offMeeting.setMeetingDate(LocalDate.parse("1998-04-16"));
        offMeeting.setMeetingTime(LocalTime.parse("12:00:00"));
        offMeeting.setCreateDate(LocalDate.now());
        offMeeting.setCreateTime(LocalTime.now());
        offMeeting.setDescription("nope");
        offMeeting.setStatus(Status.PENDING);
        offMeetingRepository.save(offMeeting);


    }
    private final MapperManager mapperManager = MapperManager.getInstance();
    @Test
    public void searchCar(){
        Car a = carRepository.findById("81a98b04-ec34-443f-b381-db23f87fa03f").get();
        CarDto aDto = mapperManager.getCarMapper().toDto(a);
        System.out.println("a:"+a);
        System.out.println(a.getName());
        System.out.println("aDto:"+aDto.getId());
    }
    @Test
    public void testLoadOrderBy(){
        LocalDate date = LocalDate.now();

        List<Post> postList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarMakeWithPriceASC("honda", PageRequest.of(0, 9),date).forEach(x->{postList.add(x);});
        postList.forEach(x->{
            System.out.println(x.getCar().getName()+", price:"+x.getCar().getPrice());
        });
        List<Post> postList1 = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarMakeWithPriceDESC("honda", PageRequest.of(0, 9),date).forEach(x->{postList1.add(x);});
        postList1.forEach(x->{
            System.out.println(x.getCar().getName()+", price:"+x.getCar().getPrice());
        });
    }
    @Test
    public void loadCarByMake(){
        String value = "Lamborghini";
        List<Post> list = new ArrayList<>() ;
        System.out.println("suize");
        LocalDate date = LocalDate.now();
        postRepository.findAllByStatusIsAndCarMake(value, PageRequest.of(0, 9),date).forEach( x -> {list.add(x);});
        list.forEach(x->{
            System.out.println(x);
        });
        System.out.println(list.size());
    }
    @Test
    public void getEnvvar(){
        String awsSecretKey = System.getProperty("aws_access_key");
        System.out.println(awsSecretKey );
        String awsSecretKey1 = System.getenv("aws_access_key");
        System.out.println(awsSecretKey1 );

    }
}
