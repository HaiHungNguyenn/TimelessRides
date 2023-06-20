package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private final MapperManager mapperManager = MapperManager.getInstance();

    public boolean isAuthenticated(){
        return(session.getAttribute("client")!=null);
    }

    @GetMapping ("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/index");
        service.configSearchList();
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
@GetMapping("/post_car")
public ModelAndView postCar(){
    ModelAndView modelAndView = new ModelAndView();
    if(!isAuthenticated()) {
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }
    modelAndView.setViewName("views/user/post-car");
    return modelAndView;
}
//    @RequestMapping("/car2")
//    public ModelAndView showCar2List(String direction,
//                                    String property,
//                                    @Nullable @RequestParam("page") Integer offset ){
//        ModelAndView modelAndView = new ModelAndView();
//
//
//        offset = (offset == null) ? 1: offset;
//
//        List<CarDto> carList;
//
//        if(property != null && direction != null){
//            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//            carList = service.getCarService().getCarSortedPerPage(PageRequest.of(offset -1, 9,Sort.by(sortDirection,property)));
//
//        }else{
//            carList = service.getCarService().getCarPerPage(PageRequest.of(offset - 1, 9));
//        }
//        long lastOffSet = service.getCarService().getLastOffset(9);
//
//        modelAndView.addObject("carlist",carList);
////        session.setAttribute("staticList",carList);
//        modelAndView.addObject("offset", offset);
//        modelAndView.addObject("property", property);
//        modelAndView.addObject("direction", direction);
//        modelAndView.addObject("lastOffset", lastOffSet);
//        modelAndView.setViewName("views/user/car");
//        return modelAndView;
//    }
    @RequestMapping("/car")
    public ModelAndView showCarList(String direction,
                                    String property,
                                    String value,
                                    @Nullable @RequestParam("offset") Integer offset ){
        ModelAndView modelAndView = new ModelAndView();

        offset = (offset == null) ? 1: offset;
        if(property != null){
            if (property.isBlank()){
                property = null;
                value = null;
            }
        }

        List<PostDto> postDto = new ArrayList<>();

        if(property != null && value != null){
            switch (property) {
                case "make" -> {
                    if (direction != null) {
                        postDto = service.getPostService().searchOrderedApprovedCarByMake(value, PageRequest.of(offset - 1, 9), direction);
                    } else {
                        postDto = service.getPostService().searchApprovedCarByMake(value, PageRequest.of(offset - 1, 9));
                    }
                }
                case "model" -> {
                    if (direction != null) {
                        postDto = service.getPostService().searchOrderedApprovedCarByModel(value, PageRequest.of(offset - 1, 9), direction);
                    } else {
                        postDto = service.getPostService().searchApprovedCarByModel(value, PageRequest.of(offset - 1, 9));
                    }
                }
                case "body" -> {
                    if (direction != null) {
                        postDto = service.getPostService().searchOrderedApprovedCarByBody(value, PageRequest.of(offset - 1, 9), direction);
                    } else {
                        postDto = service.getPostService().searchApprovedCarByBody(value, PageRequest.of(offset - 1, 9));
                    }
                }
                case "tranmision" -> {
                    if (direction != null) {
                        postDto = service.getPostService().searchOrderedApprovedCarByTran(value, PageRequest.of(offset - 1, 9), direction);
                    } else {
                        postDto = service.getPostService().searchApprovedCarByTran(value, PageRequest.of(offset - 1, 9));
                    }
                }
                case "fuel" -> {
                    if (direction != null) {
                        postDto = service.getPostService().searchOrderedApprovedCarByFuel(value, PageRequest.of(offset - 1, 9), direction);
                    } else {
                        postDto = service.getPostService().searchApprovedCarByFuel(value, PageRequest.of(offset - 1, 9));
                    }
                }
            }

        }else if(direction != null){
            postDto = service.getPostService().findSortedApprovedPosts(PageRequest.of(offset - 1, 9), direction);
        }else{
            postDto = service.getPostService().getApprovedPostsByStatus(PageRequest.of(offset - 1, 9));
        }
        long lastOffSet = service.getCarService().getLastOffset(9);

        modelAndView.addObject("postDto", postDto)
                .addObject("offset", offset)
                .addObject("property", property)
                .addObject("value", value)
                .addObject("direction", direction)
                .addObject("lastOffset", lastOffSet)
                .setViewName("views/user/car");

        service.configSearchList();
        return modelAndView;
    }

    @RequestMapping("/car/sorted-{direction}")
    public ModelAndView showCarSortedPerPage(@PathVariable String direction,
                                             @Nullable @RequestParam("property") String property,
                                             @Nullable @RequestParam("value") String value,
                                             @Nullable @RequestParam("offset") Integer offset){
        return showCarList(direction, property, value, offset);
    }

    @RequestMapping("/car/searchCarBy-{property}-{value}")
    public ModelAndView searchCarByProperties(@PathVariable String property,
                                              @PathVariable String value,
                                              @Nullable @RequestParam("direction") String direction,
                                              @Nullable @RequestParam("offset") Integer offset){
        return showCarList(direction,property,value,offset);
    }


    @GetMapping("/car/bookmeeting")
    public ModelAndView bookMeeting(@RequestParam("slot") String slot,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("carId") String carId,
                                    @RequestParam("description") String description){
        
        if(!isAuthenticated()) {
            return new ModelAndView("views/user/login");
        }
        
        String[] parts = Util.splitDateTimeString(slot);
        OffMeeting offMeeting = OffMeeting.builder()
                .client(mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client")))
                .meetingDate(Util.parseLocalDate(parts[0]))
                .meetingTime(Util.parseLocalTime(parts[1]))
                .phone(phone)
                .createDate(LocalDate.now())
                .createTime(LocalTime.now())
                .description(description)
                .car(service.getCarService().findCarEntityById(carId))
                .status(Status.PENDING)
                .build();

        service.getOffMeetingService().save(offMeeting);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("carDto",service.getCarService().findCarById(carId))
                .setViewName("views/user/car-details");
        modelAndView.addObject("status","success");
        modelAndView.addObject("message","Your meeting request has been sent. Please wait for respond");
        return modelAndView;
    }
    @GetMapping ("/car-detail/{id}")
    public ModelAndView carDetail(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("carDto",service.getCarService().findCarById(id))
                .setViewName("views/user/car-details");

        return modelAndView;
    }

    @RequestMapping("/testadd")
    public ModelAndView test(){
        OffMeeting offMeeting = new OffMeeting();
        offMeeting.setClient( mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client")));
        offMeeting.setMeetingDate(LocalDate.of(2023,6,5));
        offMeeting.setMeetingTime(LocalTime.of(22,39));
        offMeeting.setPhone("0988763136");
        offMeeting.setCreateDate(LocalDate.now());
        offMeeting.setCreateTime(LocalTime.now());
        offMeeting.setDescription("hello 123 abgh em");
        offMeeting.setCar(service.getCarService().findCarEntityById("0ee0327a-3254-4b84-b169-7b0dda68372b"));
        offMeeting.setStatus(Status.PENDING);
        service.getOffMeetingService().save(offMeeting);
        ModelAndView modelAndView = new ModelAndView("views/user/index");return modelAndView;

    }

    
    @GetMapping ("/account")
    public ModelAndView account(){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(!isAuthenticated()) {
            return modelAndView;
        }
        modelAndView.setViewName("views/user/account");
        stack.push(request.getRequestURI());
        return modelAndView;
    }

    @GetMapping ("/transactions_history")
    public ModelAndView transactionsHistory(){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(!isAuthenticated()) {
            return modelAndView;
        }

        ClientDto client = (ClientDto) session.getAttribute("client");
        List<InvoiceDto> invoiceList = service.getInvoiceService().findByClient(mapperManager.getClientMapper().toEntity(client));

        modelAndView.addObject("invoiceList", invoiceList)
                .setViewName("views/user/transactions-history");

        return modelAndView;
    }

    @GetMapping ("/meeting_history")
    public ModelAndView meetingHistory(String errorMsg, String successMsg){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()) {
            return modelAndView;
        }

        ClientDto client = (ClientDto) session.getAttribute("client");
        List<OffMeetingDto> meetingList = service.getOffMeetingService().getOffMeetingsByClient(client, PageRequest.of(0,5));

        modelAndView.addObject("meetingList", meetingList)
                .addObject("errorMsg", errorMsg)
                .addObject("successMsg", successMsg)
                .setViewName("views/user/meeting-history");

        return modelAndView;
    }


    @GetMapping ("/customer_service")
    public ModelAndView customerService(){
        return new ModelAndView("views/user/customer-service");
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
                                    @RequestParam("plan") String plan,
                                    @PathVariable("clientId") String clientId){

        ModelAndView modelAndView = new ModelAndView("views/user/post-car");

        Car car = new Car();
        List<CarImage> carImageList = new ArrayList<>();
        List<Showroom> showroomList = service.getShowroomService().findAll();
        Client client = service.getClientService().findEntityById(clientId);

        CarDescription carDescription = CarDescription.builder()
                .make(make)
                .model(model)
                .bodyColor(bodyColor)
                .interiorColor(interiorColor)
                .interiorMaterial(interiorMaterial)
                .body(body)
                .licensePlate(licensePlate)
                .fuelType(fuelType)
                .transmission(transmission)
                .firstRegistration(firstRegistration)
                .seats((Objects.equals(seats, "")) ? 0 : Integer.parseInt(seats))
                .power((Objects.equals(power, "")) ? 0 : Integer.parseInt(power))
                .engineCapacity((Objects.equals(engineCapacity, "")) ? 0 : Integer.parseInt(engineCapacity))
                .co2Emission((Objects.equals(co2Emission, "")) ? 0 : Integer.parseInt(co2Emission))
                .kmsDriven((Objects.equals(mileage, "")) ? 0 : Integer.parseInt(mileage))
                .others(others)
                .build();

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

        car.getCarImageList().addAll(carImageList);
        car.setName(carName);
        car.setPrice((Objects.equals(price, "")) ? 0 : Long.parseLong(price));
        car.setStatus("Available on market");
        car.setCarDescription(carDescription);
        car.setShowroom(showroomList.get(0));

        Post post = Post.builder()
                .car(car)
                .client(client)
                .postDate(LocalDate.now())
                .postTime(LocalTime.now())
                .status(Status.PENDING)
                .description(postDescription)
                .plan(plan)
                .build();

        service.getCarService().save(car);
        service.getPostService().save(post);

        modelAndView.addObject("successMsg", "Your post request has been received! Wait for approval");

        return modelAndView;
    }

    @RequestMapping("/meeting-history/cancel")
    public ModelAndView cancelMeeting(@RequestParam("id") String id){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(id);

        if(offMeeting == null){
            errorMsg = "An error occurred";
            return meetingHistory(errorMsg, successMsg);
        }

        if(!offMeeting.getClient().getId().equals(mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client")).getId())){
            errorMsg = "You don't have permission to cancel this meeting";
            return meetingHistory(errorMsg, successMsg);
        }

        if(offMeeting.getStaff() != null){
            String msg = "Your meeting with " + offMeeting.getClient() + " at " + offMeeting.getMeetingTime() + ", " + offMeeting.getMeetingDate() + " has been cancelled";
            service.sendNotification(offMeeting.getStaff(), msg);
        }

        service.getOffMeetingService().delete(offMeeting);
        successMsg = "Your meeting has been cancelled";

        return meetingHistory(errorMsg, successMsg);
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
    @RequestMapping("/update-info")
    public ModelAndView update(@RequestParam("name") String name,
                               @RequestParam("phone") String phone,
                               @RequestParam("address") String address){
        ModelAndView modelAndView = new ModelAndView();
        ClientDto clientDto = (ClientDto)session.getAttribute("client");
        Client client = service.getClientService().findEntityById(clientDto.getId());
        client.setName(name);
        client.setPhone(phone);
        client.setAddress(address);
        service.getClientService().save(client);
        session.setAttribute("client",mapperManager.getClientMapper().toDto(client));

        modelAndView.addObject("status","success");
        modelAndView.addObject("message","Your information has been updated successfully");

        modelAndView.setViewName("views/user/account");
        return modelAndView;
    }

    @RequestMapping("/check-notification")
    @ResponseBody
    public List<ClientNotificationDto> checkNotification(@RequestParam("id") String id){
        ClientDto client = service.getClientService().findById(id);
        List<ClientNotificationDto> notificationList = service.getClientNotificationService().findNotificationsByClient(mapperManager.getClientMapper().toEntity(client));

        if (client == null){
            return new ArrayList<>();
        }

        return notificationList;
    }

}
