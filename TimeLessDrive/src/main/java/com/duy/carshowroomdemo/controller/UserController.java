package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.service.OffMeetingService;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Controller

public class UserController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    public final Stack<String> stack = new Stack<>();
    public boolean isAuthenticated(){
        return(session.getAttribute("client")!=null);
    }

    @GetMapping ("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }
//    @GetMapping ("/car")
//    public ModelAndView car(){
//        ModelAndView modelAndView = new ModelAndView();
//        Car carByName = service.getCarService().findCarByName("Renault Scenic TCe 140 EDC GPF 103 kW");
//        modelAndView.addObject("car", carByName);
//        modelAndView.setViewName("views/user/car");
////        session.setAttribute("carList",service.getCarService().getCarList());
//        modelAndView.addObject("carList",service.getCarService().getCarList());
//        return modelAndView;
//    }

    @RequestMapping("/car")
    public ModelAndView showCarList(String direction,
                                    String property,
                                    @Nullable @RequestParam("page") Integer offset ){
        ModelAndView modelAndView = new ModelAndView();


        offset = (offset == null) ? 1: offset;

        List<CarDto> carList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            carList = service.getCarService().getCarSortedPerPage(PageRequest.of(offset -1, 9,Sort.by(sortDirection,property)));

        }else{
            carList = service.getCarService().getCarPerPage(PageRequest.of(offset - 1, 9));
        }
        long lastOffSet = service.getCarService().getLastOffset(9);

        modelAndView.addObject("carlist",carList);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("lastOffset", lastOffSet);
        modelAndView.setViewName("views/user/car");
        return modelAndView;
    }

    @RequestMapping("/car/sorted-by-{property}-{direction}")
    public ModelAndView showCarSortedPerPage(@PathVariable String direction,
                                             @PathVariable String property,
                                             @Nullable @RequestParam("page") Integer offset){
        System.out.println("**************************************");
        return showCarList(direction, property, offset);

    }













    @GetMapping ("/account")
    public ModelAndView account(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/account");
        stack.push(request.getRequestURI());
        return modelAndView;

    }
    @GetMapping ("/transactions_history")
    public ModelAndView transactionsHistory(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/transactions-history");
        return modelAndView;
    }
    @GetMapping ("/meeting_history")
    public ModelAndView meetingHistory(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/meeting-history");
        return modelAndView;
    }
    @GetMapping ("/car-detail/{id}")
    public ModelAndView carDetail(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("cardto: "+service.getCarService().findCarById(id));
        modelAndView.addObject("carDto",service.getCarService().findCarById(id));
        modelAndView.setViewName("views/user/car-details");
        return modelAndView;
    }
    @GetMapping ("/post_car")
    public ModelAndView postCar(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/post-car");
        return modelAndView;
    }
    @GetMapping ("/customer_service")
    public ModelAndView customerService(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/customer-service");
        return modelAndView;
    }

    @RequestMapping("/confirm-post/{clientId}")
    public ModelAndView confirmPost(@Nullable @RequestParam("files") MultipartFile[] files,
                                    @RequestParam("carName") String carName,
                                    @RequestParam("price") String price,
                                    @RequestParam("make") String make,
                                    @RequestParam("model") String model,
                                    @RequestParam("bodyColor") String bodyColor,
                                    @RequestParam("interiorColor") String interiorColor,
                                    @RequestParam("interiorMaterial") String interiorMaterial,
                                    @RequestParam("body") String body,
                                    @RequestParam("licensePlate") String licensePlate,
                                    @RequestParam("transmission") String transmission,
                                    @RequestParam("seats") String seats,
                                    @RequestParam("mileage") String mileage,
                                    @RequestParam("engineCapacity") String engineCapacity,
                                    @RequestParam("power") String power,
                                    @RequestParam("co2Emission") String co2Emission,
                                    @RequestParam("fuelType") String fuelType,
                                    @RequestParam("firstRegistration") String firstRegistration,
                                    @RequestParam("others") String others,
                                    @RequestParam("postDescription") String postDescription,
                                    @PathVariable("clientId") String clientId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/post-car");

        Car car = new Car();
        CarDescription carDescription = new CarDescription();
        Post post = new Post();
        List<CarImage> carImageList = new ArrayList<>();
        List<Showroom> showroomList = service.getShowroomService().findAll();
        Client client = service.getClientService().findEntityById(clientId);

        carDescription.setMake(make);
        carDescription.setModel(model);
        carDescription.setBodyColor(bodyColor);
        carDescription.setInteriorColor(interiorColor);
        carDescription.setInteriorMaterial(interiorMaterial);
        carDescription.setBody(body);
        carDescription.setLicensePlate(licensePlate);
        carDescription.setFuelType(fuelType);
        carDescription.setTransmission(transmission);
        carDescription.setFirstRegistration(firstRegistration);
        carDescription.setSeats((Objects.equals(seats, "")) ? 0 : Integer.parseInt(seats));
        carDescription.setPower((Objects.equals(power, "")) ? 0 : Integer.parseInt(power));
        carDescription.setEngineCapacity((Objects.equals(engineCapacity, "")) ? 0 : Integer.parseInt(engineCapacity));
        carDescription.setCo2Emission((Objects.equals(co2Emission, "")) ? 0 : Integer.parseInt(co2Emission));
        carDescription.setKmsDriven((Objects.equals(mileage, "")) ? 0 : Integer.parseInt(mileage));
        carDescription.setOthers(others);

        if(files != null){
            for (MultipartFile file: files) {
                CarImage carImage = new CarImage();
                try {
                    carImage.setContent(file.getBytes());
                } catch (IOException e) {
                    modelAndView.addObject("errorMsg", "An error occurred");
                    return modelAndView;
                }
                carImage.setCar(car);
                carImageList.add(carImage);
            }
        }

        if(car.getCarImageList() == null){
            car.setCarImageList(new ArrayList<>());
        }

        car.getCarImageList().addAll(carImageList);

        car.setName(carName);
        car.setPrice((Objects.equals(price, "")) ? 0 : Long.parseLong(price));
        car.setStatus("Available on market");
        car.setCarDescription(carDescription);
        car.setShowroom(showroomList.get(0));

        post.setCar(car);
        post.setClient(client);
        post.setPostDate(LocalDate.now());
        post.setPostTime(LocalTime.now());
        post.setStatus(Status.PENDING);
        post.setDescription(postDescription);

        service.getCarService().save(car);
        service.getPostService().save(post);

        modelAndView.addObject("successMsg", "Your post request has been received! Wait for approval");

        return modelAndView;
    }

    @GetMapping ("/signin")
    public ModelAndView signIn(OAuth2AuthenticationToken token){
        if(token != null){
            System.out.println(Objects.requireNonNull(token.getPrincipal().getAttribute("email")).toString());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }

    @RequestMapping("/google-handler")
    public ModelAndView googleHandler(OAuth2AuthenticationToken token){

            System.out.println(token.getPrincipal().getAttribute("email").toString());
            token.getPrincipal().getAttributes().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });


        return home();
    }
    @RequestMapping("/log-out")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("client");
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }
    @RequestMapping("/bookmeeting")
    public ModelAndView book(@RequestParam("carID")String carID, @RequestParam("bookingDate") String date, @RequestParam("bookingTime") String time, @RequestParam("descrip") String descrip){
        ModelAndView modelAndView = new ModelAndView();

        OffMeeting offMeeting = new OffMeeting();
        offMeeting.setClient((Client)session.getAttribute("client"));
        offMeeting.setMeetingDate(LocalDate.parse(date));
        offMeeting.setMeetingTime(LocalTime.parse(time));
        offMeeting.setCreateDate(LocalDate.now());
        offMeeting.setCreateTime(LocalTime.now());
        offMeeting.setDescription(descrip);
        offMeeting.setStatus(Status.PENDING);


        service.getOffMeetingService().save(offMeeting);
        modelAndView.setViewName("views/user/car");
        return modelAndView;

    }

}
