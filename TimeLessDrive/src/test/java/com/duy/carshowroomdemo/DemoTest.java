package com.duy.carshowroomdemo;

import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.repository.*;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Test
    public void addSampleData(){
        int cars = 5;
        Util.setupImageGallery(cars);
        addAdmin();
        addShowrooms();
        addStaff();
        addClients();
        addCarAndCarDescription(cars);
        addCarImages();
        addPosts();
        addInvoices();
        addOffMeetings();
        addFeedbacks();
    }

    @Test
    public void addShowrooms(){
        for (int i = 1; i < 5; i++) {
            Showroom showroom = new Showroom();
            showroom.setName("Showroom " + i);
            showroom.setAddress(Util.getRandAddress());
            showroom.setCity(Util.getRandCity());
            showroom.setPhone(Util.getRandPhone());
            Showroom save = showroomRepository.save(showroom);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void addStaff(){
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
        staff.setAddress(Util.getRandAddress());
        staff.setDob(Util.getRandBirthDay());
        staff.setJoinDate(LocalDate.now());
        staff.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));

        Staff save = staffRepository.save(staff);
        Assertions.assertThat(save).isNotNull();


    }

    @Test
    public void addAdmin(){
        Admin admin = new Admin();
        admin.setEmail("hai@gmail.com");
        admin.setAvatar(AVATAR_URL);
        admin.setRole("admin");
        admin.setPassword(Util.encodePassword("123"));
        admin.setName("hai");
        adminRepository.save(admin);
    }

    @Test
    public void addClients(){
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
    public void addCarAndCarDescription(int cars){
        String filePath = "src\\main\\java\\com\\duy\\carshowroomdemo\\util\\car_data.txt";
        List<Showroom> showroomList = showroomRepository.findAll();
        List<String> carNames = new ArrayList<>();
        int count = 0;

        try(FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            CarDescription carDescription = new CarDescription();
            Car car = new Car();
            while((line = bufferedReader.readLine()) != null){
                if(line.contains("START")){

                    count++;
                    if(count > cars){
                        break;
                    }

                    carDescription = new CarDescription();
                    car = new Car();

                } else if (line.contains("END")) {

                    if(carNames.contains(car.getName())){
                        continue;
                    }
                    carDescription.setLicensePlate(Util.getRandLicensePlate());
                    carDescription.setOthers(Util.getRandText(15));
                    carDescription.setCar(car);
                    car.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));
                    car.setPrice(Util.getRandPrice());
                    car.setCarDescription(carDescription);
                    car.setStatus("Available");
                    carNames.add(car.getName());
                    carRepository.save(car);

                } else if (line.contains("Car name")) {

                    car.setName(Util.handleDirName(line.replace("Car name:", "").trim()));

                } else if (line.contains("Make")) {

                    carDescription.setMake(line.replace("Make:", "").trim());

                } else if (line.contains("Model")) {

                    carDescription.setModel(line.replace("Model:", "").trim());

                } else if (line.contains("Body color")) {

                    carDescription.setBodyColor(line.replace("Body color:", "").trim());

                } else if (line.contains("Interior color")) {

                    carDescription.setInteriorColor(line.replace("Interior color:", "").trim());

                } else if (line.contains("Interior material")) {

                    carDescription.setInteriorMaterial(line.replace("Interior material:", "").trim());

                } else if (line.contains("Body")) {

                    carDescription.setBody(line.replace("Body:", "").trim());

                } else if (line.contains("Seats")) {

                    carDescription.setSeats(Util.handleInteger(line.replace("Seats:", "").trim()));

                } else if (line.contains("Fuel")) {

                    carDescription.setFuelType(line.replace("Fuel:", "").trim());

                } else if (line.contains("Transmission")) {

                    carDescription.setTransmission(line.replace("Transmission:", "").trim());

                } else if (line.contains("Power")) {

                    carDescription.setPower(Util.handleInteger(line.replace("Power:", "").trim()));

                } else if (line.contains("Engine capacity")) {

                    carDescription.setPower(Util.handleInteger(line.replace("Engine capacity:", "").trim()));

                } else if (line.contains("CO2 emission")) {

                    carDescription.setCo2Emission(Util.handleInteger(line.replace("CO2 emission:", "").trim()));

                } else if (line.contains("Kms driven")) {

                    carDescription.setKmsDriven(Util.handleInteger(line.replace("Kms driven:", "").trim()));

                } else if (line.contains("First registration")) {

                    carDescription.setFirstRegistration(line.replace("First registration:", "").trim());

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void addCarImages(){
        String rootPath = "D:\\Image_Gallery";
        File file = new File(rootPath);
        String[] directories = file.list();
        assert directories != null;
        Arrays.stream(directories).forEach((directory) ->{
            String subDirectory = rootPath + "\\" + directory;
            Car car = carRepository.findByName(directory);
            if(car != null){

                File file1 = new File(subDirectory);
                String[] images = file1.list();
                assert images != null;
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

            }
        });
    }

    @Test
    public void addPosts(){
        List<Car> carList = new ArrayList<>(carRepository.findAll());
        List<Client> clientList = new ArrayList<>(clientRepository.findAll());


        carList.forEach(x -> {
            Post post = new Post();
            post.setCar(x);
            post.setClient(clientList.get(Util.getRandInt(clientList.size())));
            post.setDescription(Util.getRandText(30));
            post.setPostTime(LocalTime.now());
            post.setStatus("Pending");
            post.setPostDate(Util.getRandDate(LocalDate.of(2019,1,1), LocalDate.of(2021,12,31)));
            post.setPostTime(LocalTime.now());
            Post save = postRepository.save(post);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void addInvoices(){

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
            invoice.setCreateTime(LocalTime.now());
            invoice.setStatus("Paid");
            invoice.setTax(Util.getRandText(5));
            invoice.setOtherInformation(Util.getRandText(50));

            Invoice save = invoiceRepository.save(invoice);

            Assertions.assertThat(save).isNotNull();
        });
    }

    @Test
    public void addOffMeetings(){
//        List<Staff> staffList = new ArrayList<>(staffRepository.findAll());
        List<Client> clientList = clientRepository.findAll();
        List<Car> carList = carRepository.findAll();
        for (int i = 0; i < 100; i++) {
            OffMeeting offMeeting = new OffMeeting();
//            offMeeting.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            offMeeting.setClient(clientList.get(Util.getRandInt(clientList.size())));
            offMeeting.setCar(carList.get(Util.getRandInt(carList.size())));
            offMeeting.setMeetingDate(Util.getRandDate(LocalDate.of(2023, 4,1), LocalDate.of(2023, 8,15)));
            offMeeting.setCreateDate(Util.getRandDate(LocalDate.of(2023, 5,20), LocalDate.now()));
            offMeeting.setMeetingTime(LocalTime.now());
            offMeeting.setCreateTime(LocalTime.now());
            offMeeting.setDescription(Util.getRandText(30));
            offMeeting.setPhone(Util.getRandPhone());
            offMeeting.setStatus(Status.PENDING);

            OffMeeting save = offMeetingRepository.save(offMeeting);

            Assertions.assertThat(save).isNotNull();
        }
    }

    @Test
    public void addFeedbacks(){
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

    @Test
    public void changePostStatus(){
//        List<Post> all = postRepository.findAll();
//        all.forEach((x) -> {
//            x.setStatus(Status.PENDING);
//            postRepository.save(x);
//        });

        offMeetingRepository.findAll().forEach((x) -> {
            x.setStatus(Status.PENDING);
            x.setStaff(null);
            x.setMeetingTime(LocalTime.now());
            x.setMeetingDate(Util.getRandDate(LocalDate.of(2023, 4,1), LocalDate.of(2023, 8,1)));
//            x.setCreateTime(LocalTime.now());
            offMeetingRepository.save(x);
        });
    }

    @Test
    public void testNewTable(){
//        Staff staff = staffRepository.findAll().get(0);
//        OffMeeting offMeeting = offMeetingRepository.findAll().get(0);
//
        Client client = clientRepository.findByEmail("lynnette.hansen@example.com").orElse(null);

        if(client != null){
            if(client.getOffMeetingList() == null){
                client.setOffMeetingList(new ArrayList<>());
            }
            if (client.getPostList() == null){
                client.setPostList(new ArrayList<>());
            }
        }else {
            return;
        }

        for (int i = 0; i < 20; i++) {
            OffMeeting offMeeting = new OffMeeting();
//            offMeeting.setStaff(staffList.get(Util.getRandInt(staffList.size())));
            offMeeting.setClient(client);
            offMeeting.setMeetingDate(Util.getRandDate(LocalDate.of(2023, 7,1), LocalDate.of(2023, 9,1)));
            offMeeting.setCreateDate(Util.getRandDate(LocalDate.of(2023, 5,20), LocalDate.now()));
            offMeeting.setDescription(Util.getRandText(30));
            offMeeting.setStatus("Not yet");
            client.getOffMeetingList().add(offMeeting);
        }

        List<Showroom> showroomList = showroomRepository.findAll();

        for (int i = 0; i < 20; i++) {
            Car car = new Car();
            CarDescription carDescription = new CarDescription();
            car.setName("Car test " + i);

            carDescription.setLicensePlate(Util.getRandLicensePlate());
            carDescription.setOthers(Util.getRandText(15));
            carDescription.setCar(car);
            car.setShowroom(showroomList.get(Util.getRandInt(showroomList.size())));
            car.setPrice(Util.getRandPrice());
            car.setCarDescription(carDescription);
            car.setStatus("Available");

            Post post = new Post();
                post.setCar(car);
                post.setClient(client);
                post.setDescription(Util.getRandText(30));
                post.setStatus("Pending");
                post.setPostDate(Util.getRandDate(LocalDate.of(2019,1,1), LocalDate.of(2021,12,31)));
                client.getPostList().add(post);
        }

        clientRepository.save(client);
    }

    @Test
    public void test(){
        System.out.println(Util.calculateTotal(10023023L, "sdfk15%"));


    }

}
