package com.duy.carshowroomdemo;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.*;
import com.duy.carshowroomdemo.service.ClientService;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DemoTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CarDescriptionRepository carDescriptionRepository;
    @Autowired
    private CarImageRepository carImageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private OffMeetingRepository offMeetingRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private StaffRepository staffRepository;

    public final String AVATAR_URL = "https://icon-library.com/images/anonymous-avatar-icon/anonymous-avatar-icon-25.jpg";
    public final String CAR_IMAGE_URL = "https://images.unsplash.com/photo-1502877338535-766e1452684a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=872&q=80";

    @Test
    public void testAddShowroom(){
        Showroom showroom = new Showroom();
        showroom.setName("Showroom 4");
        showroom.setAddress("123 sample address");
        showroom.setCity("Thai Nguyen");
        showroom.setPhone(Util.getRandPhone());
        Showroom save = showroomRepository.save(showroom);

        Assertions.assertThat(save).isNotNull();

    }

    @Test
    public void testAddStaff(){
        for (int i = 0; i < 100; i++) {
            List<Showroom> showroomList = new ArrayList<>();
            showroomRepository.findAll().forEach(showroomList::add);
            Staff staff = new Staff();
            staff.setRole("staff");
            staff.setName(Util.getRandName());
            staff.setAvatar(AVATAR_URL);
            staff.setEmail(Util.getRandEmail());
            staff.setPhone(Util.getRandPhone());
            staff.setGender(Util.getRandGender());
            staff.setPassword("password123");
            staff.setAddress("123 sample address");
            staff.setDob(LocalDate.now());
            staff.setJoinDate(LocalDate.now());
            staff.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));

            Staff save = staffRepository.save(staff);
            Assertions.assertThat(save).isNotNull();
        }

        List<Showroom> showroomList = new ArrayList<>();
        showroomRepository.findAll().forEach(showroomList::add);
        Staff staff = new Staff();
        staff.setRole("staff");
        staff.setName(Util.getRandName());
        staff.setAvatar(AVATAR_URL);
        staff.setEmail("duy@gmail.com");
        staff.setPhone(Util.getRandPhone());
        staff.setGender("male");
        staff.setPassword("123");
        staff.setAddress("123 sample address");
        staff.setDob(LocalDate.now());
        staff.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));

        Staff save = staffRepository.save(staff);
        Assertions.assertThat(save).isNotNull();


    }

    @Test
    public void testAddClient(){
        for (int i = 0; i < 100; i++) {
            Client client = new Client();
            client.setRole("client");
            client.setName(Util.getRandName());
            client.setAvatar(AVATAR_URL);
            client.setPhone(Util.getRandPhone());
            client.setEmail(Util.getRandEmail());
            client.setPassword("password123");
            client.setAddress("123 sample address");
            client.setGender(Util.getRandGender());
            client.setDob(LocalDate.now());
            client.setTax("I dont know this field");

            Client save = clientRepository.save(client);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddCarDescription(){
        for (int i = 0; i < 100; i++) {
            CarDescription carDescription = new CarDescription();
            carDescription.setColor(Util.getRandColor());
            carDescription.setFuelType(Util.getRandFuelType());
            carDescription.setNoOfSeat((short) Util.getRandInt(4,8));
            carDescription.setHp((short) (Util.getRandInt(1800) + 200));
            carDescription.setWheelSize((short) (Util.getRandInt(35) + 35));
            carDescription.setBoughtYear((short) (Util.getRandInt(30) + 1990));
            carDescription.setWidth((short) (Util.getRandInt(50) + 150));
            carDescription.setLength((short) (Util.getRandInt(50) + 400));
            carDescription.setHeight((short) (Util.getRandInt(10) + 175));
            carDescription.setKmSpend("i dont know this field");
            carDescription.setManufacturedYear((short) (carDescription.getBoughtYear() - Util.getRandInt(5)));
            carDescription.setOthers("Some extra information can be provided here");
            CarDescription save = carDescriptionRepository.save(carDescription);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddCar(){
        List<CarDescription> carDescriptionList = new ArrayList<>();
        carDescriptionRepository.findAll().forEach(carDescriptionList::add);
        List<Showroom> showroomList = new ArrayList<>();
        showroomRepository.findAll().forEach(showroomList::add);
        carDescriptionList.forEach((x) -> {
            Car car = new Car();
            car.setCarDescription(x);
            car.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));
            car.setName(Util.getRandCarName());
            car.setBrand(Util.getRandBrand());
            car.setPrice(Util.getRandPrice());
            car.setStatus("Available");

            Car save = carRepository.save(car);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void testAddCarImage(){
        for (int i = 0; i < 300; i++) {
            List<Car> carList = new ArrayList<>();
            carRepository.findAll().forEach(carList::add);
            CarImage carImage = new CarImage();
            carImage.setCar(carList.get(Util.getRandInt(carList.size())));
            carImage.setLink(CAR_IMAGE_URL);

            CarImage save = carImageRepository.save(carImage);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddPost(){
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEach(carList::add);
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);

        carList.forEach(x -> {
            Post post = new Post();
            post.setCar(x);
            post.setClient(clientList.get(Util.getRandInt(clientList.size())));
            post.setDescription("Write some description for the post");
            post.setCreatedAt(LocalDate.now());

            Post save = postRepository.save(post);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void testAddInvoice(){

        List<Staff> staffList = new ArrayList<>();
        staffRepository.findAll().forEach(staffList::add);
        List<Client> clientList = new ArrayList<>();
        clientRepository.findAll().forEach(clientList::add);
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEach(carList::add);

        carList.forEach(x -> {
            Invoice invoice = new Invoice();
            invoice.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            invoice.setClient(clientList.get(Util.getRandInt(clientList.size())));
            invoice.setCar(x);
            invoice.setTotal(Util.getRandPrice());
            invoice.setCreatedAt(LocalDate.now());
            invoice.setStatus("Paid");

            Invoice save = invoiceRepository.save(invoice);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void testAddOffMeeting(){
        for (int i = 0; i < 100; i++) {
            List<Staff> staffList = new ArrayList<>();
            staffRepository.findAll().forEach(staffList::add);
            List<Client> clientList = new ArrayList<>();
            clientRepository.findAll().forEach(clientList::add);
            OffMeeting offMeeting = new OffMeeting();
            offMeeting.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            offMeeting.setClient(clientList.get(Util.getRandInt(clientList.size())));
            offMeeting.setMeetingDate(LocalDate.now());
            offMeeting.setCreateAt(LocalDate.now());
            offMeeting.setDescription("Some kinds of meeting here");
            offMeeting.setStatus("Not yet");

            OffMeeting save = offMeetingRepository.save(offMeeting);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddFeedback(){
        for (int i = 0; i < 100; i++) {
            List<Client> clientList = new ArrayList<>();
            clientRepository.findAll().forEach(clientList::add);
            Feedback feedback = new Feedback();
            feedback.setClient(clientList.get(Util.getRandInt(clientList.size())));
            feedback.setCreatedAt(LocalDate.now());
            feedback.setDescription("This is a feedback from client");

            Feedback save = feedbackRepository.save(feedback);

            Assertions.assertThat(save).isNotNull();
        }

    }

    @Test
    public void testDeleteStaff(){
        staffRepository.deleteAll();
    }

    @Test
    public void testDeleteCar(){
        List<Car> carList = new ArrayList<>();
        Iterable<Car> all = carRepository.findAll();
        all.forEach(carList::add);
        Car car = carList.get(0);
        System.out.println(car.getCarDescription().getColor());
        carRepository.delete(car);
        Iterable<Car> all1 = carRepository.findAll();

        Assertions.assertThat(all1).isEmpty();

    }

    @Test
    public void testLoadInvoice(){
        invoiceRepository.save(new Invoice());
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceRepository.findAll().forEach(invoiceList::add);
//        invoiceList.get(0).setCar(new Car());
        Invoice save = invoiceRepository.save(invoiceList.get(0));
        Assertions.assertThat(save).isNotNull();
    }

    @Test
    public void testLoadCar(){
        Car car = carRepository.findById(1).get();
        Assertions.assertThat(car).isNotNull();
    }

    @Test
    public void testArray(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Scanner scanner = new Scanner(System.in);
            list.set(i, scanner.nextInt());
        }
        list.forEach(x-> System.out.println(x));
    }

    @Test
    public void testMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findAll().forEach(x -> {
            OffMeetingDto offMeetingDto = modelMapper.map(x, OffMeetingDto.class);
            offMeetingDto.setClient(modelMapper.map(x.getClient(), ClientDto.class));
            offMeetingDto.setStaff(modelMapper.map(x.getStaff(), StaffDto.class));
            offMeetingDtoList.add(offMeetingDto);
        });

        Assertions.assertThat(offMeetingDtoList).isNotEmpty();

        OffMeetingDto offMeetingDto = offMeetingDtoList.get(0);

        System.out.println(offMeetingDto.getClient() + offMeetingDto.getMeetingDate().toString() + offMeetingDto.getDescription()
                            + offMeetingDto.getStatus() + offMeetingDto.getStaff()
        );

    }

    @Test
    public void testMappers(){
        MapperManager mapperManager = new MapperManager();
        Client client = clientRepository.findById(55).get();
        ClientDto dto = mapperManager.getClientMapper().toDto(client);
        Client entity = mapperManager.getClientMapper().toEntity(dto);

        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findOffMeetingsByClient(entity).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });

        offMeetingDtoList.forEach(x -> System.out.println(x.toString()));


    }



}
