package com.duy.carshowroomdemo;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.*;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


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
    private final Lorem lorem = new LoremIpsum();

    @Test
    public void testAddData(){
        testAddAdmin();
        testAddShowroom();
        testAddStaff();
        testAddClient();
        testAddCarDescription();
        testAddCar();
        testAddCarImage();
        testAddPost();
        testAddInvoice();
        testAddOffMeeting();
        testAddFeedback();
    }

    @Test
    public void testAddShowroom(){
        for (int i = 1; i < 5; i++) {
            Showroom showroom = new Showroom();
            showroom.setName("Showroom " + i);
            showroom.setAddress(Util.getRandAddress());
            showroom.setCity("Thai Nguyen");
            showroom.setPhone(Util.getRandPhone());
            Showroom save = showroomRepository.save(showroom);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddStaff(){
        List<Showroom> showroomList = new ArrayList<>(showroomRepository.findAll());
        for (int i = 0; i < 100; i++) {
            Staff staff = new Staff();
            staff.setRole("staff");
            staff.setName(Util.getRandName());
            staff.setAvatar(AVATAR_URL);
            staff.setPhone(Util.getRandPhone());
            staff.setGender(Util.getRandGender());
            staff.setPassword(Util.encodePassword("password123"));
            staff.setAddress(Util.getRandAddress());
            staff.setDob(Util.getRandDate(LocalDate.of(1980,1,1), LocalDate.of(2000,12,31)));
            staff.setEmail(Util.getRandEmail());
            staff.setJoinDate(Util.getRandDate(LocalDate.of(2020,1,1), LocalDate.now()));
            staff.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));

            Staff save = staffRepository.save(staff);
            Assertions.assertThat(save).isNotNull();
        }

        Staff staff = new Staff();
        staff.setRole("staff");
        staff.setName(Util.getRandName());
        staff.setAvatar(AVATAR_URL);
        staff.setEmail("duy@gmail.com");
        staff.setPhone(Util.getRandPhone());
        staff.setGender("male");
        staff.setPassword(Util.encodePassword("123"));
        staff.setAddress("123 sample address");
        staff.setDob(Util.getRandBirthDay());
        staff.setJoinDate(LocalDate.now());
        staff.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));

        Staff save = staffRepository.save(staff);
        Assertions.assertThat(save).isNotNull();


    }

    @Test
    public void testAddAdmin(){
        Admin admin = new Admin();
        admin.setEmail("hai@gmail.com");
        admin.setAvatar(AVATAR_URL);
        admin.setRole("admin");
        admin.setPassword(Util.encodePassword("123"));
        admin.setName("hai");
        adminRepository.save(admin);
    }

    @Test
    public void testAddClient(){
        for (int i = 0; i < 100; i++) {
            Client client = new Client();
            client.setRole("client");
            client.setName(Util.getRandName());
            client.setAvatar(AVATAR_URL);
            client.setPhone(Util.getRandPhone());
            client.setPassword(Util.encodePassword("password123"));
            client.setAddress(Util.getRandAddress());
            client.setGender(Util.getRandGender());
            client.setDob(Util.getRandDate(LocalDate.of(1980,1,1), LocalDate.of(2000,12,31)));
            client.setEmail(Util.getRandEmail());
            client.setJoinDate(Util.getRandDate(LocalDate.of(2020,1,1), LocalDate.now()));

            Client save = clientRepository.save(client);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddCarDescription(){
//        for (int i = 0; i < 100; i++) {
//            CarDescription carDescription = new CarDescription();
//            carDescription.setColor(Util.getRandColor());
//            carDescription.setLicensePlate(Util.getRandLicensePlate());
//            carDescription.setFuelType(Util.getRandFuelType());
//            carDescription.setNoOfSeat((short) Util.getRandInt(4,8));
//            carDescription.setHp((short) (Util.getRandInt(1800) + 200));
//            carDescription.setWheelSize((short) (Util.getRandInt(35) + 35));
//            carDescription.setBoughtYear((short) (Util.getRandInt(30) + 1990));
//            carDescription.setWidth((short) (Util.getRandInt(50) + 150));
//            carDescription.setLength((short) (Util.getRandInt(50) + 400));
//            carDescription.setHeight((short) (Util.getRandInt(10) + 175));
//            carDescription.setKmSpend(String.valueOf(Util.getRandInt(100,2000)));
//            carDescription.setManufacturedYear((short) (carDescription.getBoughtYear() - Util.getRandInt(5)));
//            carDescription.setOthers(Util.getRandText(30));
//            CarDescription save = carDescriptionRepository.save(carDescription);
//
//            Assertions.assertThat(save).isNotNull();
//        }
    }

    @Test
    public void testAddCar(){
        List<CarDescription> carDescriptionList = new ArrayList<>(carDescriptionRepository.findAll());
        List<Showroom> showroomList = new ArrayList<>(showroomRepository.findAll());
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
            List<Car> carList = new ArrayList<>(carRepository.findAll());
            CarImage carImage = new CarImage();
            carImage.setCar(carList.get(Util.getRandInt(carList.size())));
//            carImage.setLink(CAR_IMAGE_URL);

            CarImage save = carImageRepository.save(carImage);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddPost(){
        List<Car> carList = new ArrayList<>(carRepository.findAll());
        List<Client> clientList = new ArrayList<>(clientRepository.findAll());


        carList.forEach(x -> {
            Post post = new Post();
            post.setCar(x);
            post.setClient(clientList.get(Util.getRandInt(clientList.size())));
            post.setDescription(Util.getRandText(30));
            post.setStatus("Pending");
            post.setPostDate(Util.getRandDate(LocalDate.of(2019,1,1), LocalDate.of(2021,12,31)));

            Post save = postRepository.save(post);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void testAddInvoice(){

        List<Staff> staffList = new ArrayList<>(staffRepository.findAll());
        List<Client> clientList = new ArrayList<>(clientRepository.findAll());
        List<Car> carList = new ArrayList<>(carRepository.findAll());

        carList.forEach(x -> {
            Invoice invoice = new Invoice();
            invoice.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            invoice.setClient(clientList.get(Util.getRandInt(clientList.size())));
            invoice.setCar(x);
            invoice.setTotal(Util.getRandPrice());
            invoice.setCreateDate(Util.getRandDate(LocalDate.of(2020,1,1), LocalDate.now()));
            invoice.setStatus("Paid");
            invoice.setTax(Util.getRandText(5));
            invoice.setOtherInformation(Util.getRandText(50));

            Invoice save = invoiceRepository.save(invoice);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void testAddOffMeeting(){
        List<Staff> staffList = new ArrayList<>(staffRepository.findAll());
        List<Client> clientList = new ArrayList<>(clientRepository.findAll());
        for (int i = 0; i < 100; i++) {
            OffMeeting offMeeting = new OffMeeting();
            offMeeting.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            offMeeting.setClient(clientList.get(Util.getRandInt(clientList.size())));
            offMeeting.setMeetingDate(Util.getRandDate(LocalDate.of(2023, 7,1), LocalDate.of(2023, 9,1)));
            offMeeting.setCreateDate(Util.getRandDate(LocalDate.of(2023, 5,20), LocalDate.now()));
            offMeeting.setDescription(Util.getRandText(30));
            offMeeting.setStatus("Not yet");

            OffMeeting save = offMeetingRepository.save(offMeeting);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void testAddFeedback(){
        for (int i = 0; i < 100; i++) {
            List<Client> clientList = new ArrayList<>(clientRepository.findAll());
            Feedback feedback = new Feedback();
            feedback.setClient(clientList.get(Util.getRandInt(clientList.size())));
            feedback.setCreatedAt(Util.getRandDate(LocalDate.of(2022,1,1), LocalDate.now()));
            feedback.setDescription(Util.getRandText(30));

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
//        System.out.println(car.getCarDescription().getColor());
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
        Car car = carRepository.findById("1").get();
        Assertions.assertThat(car).isNotNull();
    }

    @Test
    public void testArray(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Scanner scanner = new Scanner(System.in);
            list.set(i, scanner.nextInt());
        }
        list.forEach(System.out::println);
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
        Client client = clientRepository.findById("55").get();
        ClientDto dto = mapperManager.getClientMapper().toDto(client);
        Client entity = mapperManager.getClientMapper().toEntity(dto);

        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findOffMeetingsByClient(entity, PageRequest.of(0,10)).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });

        offMeetingDtoList.forEach(x -> System.out.println(x.toString()));


    }

    @Test
    public void testLogin(){
//        offMeetingRepository.findAll(Sort.by("meetingDate")).forEach(x -> System.out.println(x.getMeetingDate()));
//        offMeetingRepository.findAll(PageRequest.of(1, 10), Sort.by("meetingDate")).forEach(x -> System.out.println(x.getMeetingDate()));
//        System.out.println("==========================================================");
//        Util.writeRandomParagraph(10000);


        System.out.println(UUID.randomUUID());
//        PageImpl page = new PageImpl<>(offMeetingRepository.findAll(), PageRequest.of(0, 10), offMeetingRepository.count());
////        System.out.println(page.getTotalElements());
////        System.out.println(page.getTotalPages());
//        offMeetingRepository.findAll(page.nextPageable()).forEach(x -> System.out.println(x.getMeetingDate()));
//        page = new PageImpl(page.getContent(), page.nextPageable(), page.getTotalElements());
//        offMeetingRepository.findAll(page.nextPageable()).forEach(x -> System.out.println(x.getMeetingDate()));



//        System.out.println("==========================================================");
//        offMeetingRepository.findAll(page.nextPageable()).forEach(x -> System.out.println(x.getMeetingDate()));




//        offMeetingRepository.findAll(PageRequest.of(0, 10)).forEach(x -> System.out.println(x.getMeetingDate()));
//        offMeetingRepository.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "meetingDate")).forEach(x -> System.out.println(x.getMeetingDate()));
//        System.out.println("==========================================================");
//        offMeetingRepository.findAll(PageRequest.of(, 10, Sort.by("meetingDate"))).forEach(x -> System.out.println(x.getMeetingDate()));
    }
    @Test
    public void testADd(){
        Client client = new Client();
        client.setRole("client");
        client.setName(Util.getRandName());
        client.setAvatar(AVATAR_URL);
        client.setPhone(Util.getRandPhone());
        client.setPassword(Util.encodePassword("password123"));
        client.setAddress("123 sample address");
        client.setGender(Util.getRandGender());
        client.setDob(Util.getRandDate(LocalDate.of(1980,1,1), LocalDate.of(2000,12,31)));
        client.setEmail("hai123@gmail.com");
        client.setJoinDate(Util.getRandDate(LocalDate.of(2020,1,1), LocalDate.now()));

        Client save = clientRepository.save(client);

        Assertions.assertThat(save).isNotNull();
    }
    MapperManager mapperManager = new MapperManager();

    @Test
    public void testViewClient() {
        List<Client> client = clientRepository.findAll();
        List<ClientDto> clientList = new ArrayList<>();
        for (Client x : client) {
            clientList.add(mapperManager.getClientMapper().toDto(x));
        }
        for (ClientDto x : clientList) {
            System.out.println(x);
        }
    }

    @Test
    public void testAddMoreClientRequest(){
        Optional<Client> byEmail = clientRepository.findByEmail("QuanDH2603@gmail.com");
//        if(byEmail.isPresent()){
//            for (int i = 0; i < 10; i++) {
//
//                List<Staff> staffList = new ArrayList<>(staffRepository.findAll());
//                OffMeeting offMeeting = new OffMeeting();
//                offMeeting.setStaff(staffList.get(Util.getRandInt(staffList.size())));
//                offMeeting.setClient(byEmail.get());
//                offMeeting.setMeetingDate(Util.getRandDate(LocalDate.of(2023, 7,1), LocalDate.of(2023, 9,1)));
//                offMeeting.setCreatedAt(Util.getRandDate(LocalDate.of(2023, 5,20), LocalDate.now()));
//                offMeeting.setDescription(Util.getRandText(30));
//                offMeeting.setStatus("Not yet");
//
//                OffMeeting save = offMeetingRepository.save(offMeeting);
//            }
//        }
        List<Post> all = postRepository.findAll();
        if(byEmail.isPresent()){
            for (int i = 0; i < 10; i++) {

                all.get(i).setClient(byEmail.get());

            }
        }
    }

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testPaging(){
//        Page<OffMeeting> page1 = offMeetingRepository.findAll(PageRequest.of(0, 10));
//        Page<OffMeeting> page2 = offMeetingRepository.findAll(page1.nextPageable());
//
//        Pageable page = PageRequest.of(0,10);
//
//
//        page1.map((element) -> modelMapper.map(element, OffMeetingDto.class)).forEach(x -> System.out.println(x.getClient().getName()));

//        System.out.println("abc de fg hijk".replaceAll(" ", ""));
        System.out.println(Util.getRandLicensePlate());

    }

    @Test
    public void testAddNewInvoice(){

        Service service = new Service();

        String email = "KhoiNH3105@gmail.com";
        String fullName = "Nguyen Huynh Khoi";
        String phone = "0921217961";
        String carName = "TRAVERSE";
        String brand = "Ferrari";
        int boughtYear = 1996;
        String licensePlate = "82B24537";
        String additionalInfo = "sdfjklsdfjklsdfj";

        StaffDto staff = modelMapper.map(staffRepository.findByEmail("duy@gmail.com").get(), StaffDto.class);



        ClientDto client = modelMapper.map(clientRepository.findByEmailAndNameAndPhone(email, fullName, phone), ClientDto.class);

        Optional<Car> carOptional = carRepository.findCarByNameAndBrand(carName, brand);
        if(carOptional.isEmpty()){
            System.out.println("null");
        }

//        if(carOptional.get().getCarDescription().getBoughtYear() != boughtYear || carOptional.get().getCarDescription().getLicensePlate().equalsIgnoreCase(licensePlate)){
//            System.out.println("null");
//        }

        Invoice invoice = new Invoice();
        invoice.setCar(modelMapper.map(carOptional.get(), Car.class));
        invoice.setClient(modelMapper.map(client, Client.class));
        invoice.setStaff(modelMapper.map(staff, Staff.class));
        invoice.setTotal(carOptional.get().getPrice());
        invoice.setTax(Util.getRandText(10));
        invoice.setStatus("Paid");
        invoice.setCreateDate(LocalDate.now());
        invoice.setOtherInformation(additionalInfo);

        Invoice save = invoiceRepository.save(invoice);

        Assertions.assertThat(save).isNotNull();
    }

    @Test
    public void testLorem() throws IOException {
//        String address = lorem.getCity() + " " + lorem.getStateFull() + " " + lorem.getCountry();
//        System.out.println(address);
//        System.out.println(new LoremIpsum().getWords(30));
//        byte[] content = imageRepo.findAll().get(0).getContent();
//
//        System.out.println(content.length);

        URL url = new URL("https://api.api-ninjas.com/v1/cars?model=camry");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseStream);
        System.out.println(root.path("fact").asText());
    }

    @Test
    public void testStoreImage() throws IOException {
        Car car = carRepository.findByName("Car for testing");
        CarImage carImage = new CarImage();
        try(FileInputStream fileInputStream = new FileInputStream("D:\\Image_Gallery\\BMW X5 xDrive 290 kW\\carvago1.jpg")){
            carImage.setContent(fileInputStream.readAllBytes());
        }
        carImage.setCar(car);
        car.getCarImageList().add(carImage);
        carRepository.save(car);
//        Car carForTesting = carRepository.findByName("Car for testing");
//        System.out.println(carForTesting.getCarImageList().size());

//        FileImageInputStream fileImageInputStream = new FileImageInputStream(new File(""));

    }

    @Test
    public void testWorkWithDirectories(){
        String rootPath = "D:\\Image_Gallery";
        File file = new File(rootPath);
        String[] directories = file.list();
        List<CarDescription> descriptionList = carDescriptionRepository.findAll();
        assert directories != null;
        AtomicInteger count = new AtomicInteger();
        Arrays.stream(directories).forEach((directory) ->{
            String subDirectory = rootPath + "\\" + directory;
            File file1 = new File(subDirectory);
            String[] images = file1.list();
            assert images != null;
            Car car = new Car();
            car.setName(directory);
            car.setCarDescription(descriptionList.get(count.getAndIncrement()));
            List<CarImage> carImageList = new ArrayList<>();
            Arrays.stream(images).forEach((image -> {
                try(FileInputStream fileInputStream = new FileInputStream(subDirectory + "\\" + image)) {
                    CarImage carImage = new CarImage();
                    carImage.setCar(car);
                    carImage.setContent(fileInputStream.readAllBytes());
                    carImageList.add(carImage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
            car.setCarImageList(carImageList);
            carRepository.save(car);
        });
    }



}
