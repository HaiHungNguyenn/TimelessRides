package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.*;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.mapper.ModelMapper;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Plan;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public boolean isAuthenticated() {
        return (session.getAttribute("client") != null);
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("views/user/index");

        service.configSearchList();

        return modelAndView
                .addObject("postList", service.getPostService().findPriorPosts());
    }

    @GetMapping("/post_car")
    public ModelAndView showCarPostingPage() {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }
        modelAndView.setViewName("views/user/post-car");
        return modelAndView;
    }

    @RequestMapping("/car")
    public ModelAndView showCarInventory(String direction,
                                         String property,
                                         String value,
                                         @Nullable @RequestParam("offset") Integer offset) {

        ModelAndView modelAndView = new ModelAndView();

        offset = (offset == null) ? 1 : offset;
        if (property != null) {
            if (property.isBlank()) {
                property = null;
                value = null;
            }
        }

        List<PostDto> postList = new ArrayList<>();

        if (property != null && value != null) {
            switch (property) {
                case "make" -> {
                    if (direction != null) {
                        postList = service.getPostService().searchOrderedApprovedCarByMake(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
                    } else {
                        postList = service.getPostService().searchApprovedCarByMake(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
                    }
                }
                case "model" -> {
                    if (direction != null) {
                        postList = service.getPostService().searchOrderedApprovedCarByModel(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
                    } else {
                        postList = service.getPostService().searchApprovedCarByModel(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
                    }
                }
                case "body" -> {
                    if (direction != null) {
                        postList = service.getPostService().searchOrderedApprovedCarByBody(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
                    } else {
                        postList = service.getPostService().searchApprovedCarByBody(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
                    }
                }
                case "tranmision" -> {
                    if (direction != null) {
                        postList = service.getPostService().searchOrderedApprovedCarByTran(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
                    } else {
                        postList = service.getPostService().searchApprovedCarByTran(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
                    }
                }
                case "fuel" -> {
                    if (direction != null) {
                        postList = service.getPostService().searchOrderedApprovedCarByFuel(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
                    } else {
                        postList = service.getPostService().searchApprovedCarByFuel(value, PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
                    }
                }
            }
        } else if (direction != null) {
            postList = service.getPostService().findSortedApprovedPosts(PageRequest.of(offset - 1, 9, Sort.by("priority").descending()), direction);
        } else {
            postList = service.getPostService().getApprovedPostsByStatus(PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
        }

        long lastOffSet = service.getPostService().getCarInventoryLastOffset(9);

        modelAndView.addObject("postDto", postList)
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
    public ModelAndView showSortedCarList(@PathVariable String direction,
                                          @Nullable @RequestParam("property") String property,
                                          @Nullable @RequestParam("value") String value,
                                          @Nullable @RequestParam("offset") Integer offset) {
        return showCarInventory(direction, property, value, offset);
    }

    @RequestMapping("/car/searchCarBy-{property}-{value}")
    public ModelAndView searchCarByProperties(@PathVariable String property,
                                              @PathVariable String value,
                                              @Nullable @RequestParam("direction") String direction,
                                              @Nullable @RequestParam("offset") Integer offset) {
        return showCarInventory(direction, property, value, offset);
    }

    @RequestMapping("/car/search")
    public ModelAndView searchCar(@RequestParam("searchKW") String keyword,
                                  @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/car");

        if (keyword.isBlank()){
            return showCarInventory(null,null, null, null);
        }

        offset = (offset == null) ? 1 : offset;

        List<PostDto> postList = service.getPostService().searchCar(PageRequest.of(offset - 1, 10, Sort.by("priority").descending()), keyword);

        modelAndView.addObject("postDto", postList)
                .addObject("offset", offset);
        return modelAndView;
    }

    @RequestMapping("/search-cars-by-price")
    public ModelAndView searchCarsByPrice(@RequestParam("filterAmount") String amount,
                                          @Nullable @RequestParam("offset") Integer offset){

        ModelAndView modelAndView = new ModelAndView("views/user/car");

        offset = (offset == null) ? 1 : offset;

        Map<String, Long> splitPrice = Util.processPriceRange(amount);

        Long lower = splitPrice.get("lower");
        Long upper = splitPrice.get("upper");

        List<PostDto> postList = service.getPostService().findPostsInPriceRange(lower, upper, PageRequest.of(offset - 1, 10));
        System.out.println("The list size is: " + postList.size());

        return modelAndView.addObject("postDto", postList)
                .addObject("offset", offset);
    }

    @GetMapping("/car/book-meeting")
    public ModelAndView bookMeeting(@RequestParam("slot") String slot,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("carId") String carId,
                                    @RequestParam("description") String description) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        Car car = service.getCarService().findCarEntityById(carId);
        Client clientEntity = service.getClientService().findEntityById(((ClientDto) session.getAttribute("client")).getId());

        if (car.getPost().getClient().equals(clientEntity)){
            modelAndView.addObject("carDto", service.getCarService().findCarById(carId))
                    .addObject("status", "fail")
                    .addObject("message", "You are not allowed to book meeting for this car")
                    .setViewName("views/user/car-details");
            return modelAndView;
        }

        String[] parts = Util.splitDateTimeString(slot);
        List<OffMeeting> offMeetingList = car.getOffMeetingList();

        for (OffMeeting meeting : offMeetingList){
            if (meeting.getMeetingDate().equals(Util.parseLocalDate(parts[0]))
                    && meeting.getMeetingTime().equals(Util.parseLocalTime(parts[1]))
                    && Objects.equals(meeting.getStatus(), Status.PENDING)){
                if(meeting.getClient().getId() == ((ClientDto) session.getAttribute("client")).getId()){
                    modelAndView.addObject("carDto", service.getCarService().findCarById(carId))
                            .addObject("status", "fail")
                            .addObject("message", "You have booked this slot already.")
                            .setViewName("views/user/car-details");
                    return modelAndView;
                }
                modelAndView.addObject("carDto", service.getCarService().findCarById(carId))
                        .addObject("status", "fail")
                        .addObject("message", "This car has been booked for another meeting at this time. Please choose another time.")
                        .setViewName("views/user/car-details");
                return modelAndView;
            }
        }

        OffMeeting offMeeting = OffMeeting.builder()
                .client(mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client")))
                .meetingDate(Util.parseLocalDate(parts[0]))
                .meetingTime(Util.parseLocalTime(parts[1]))
                .phone(phone)
                .createDate(LocalDate.now())
                .createTime(LocalTime.now())
                .description(description)
                .car(car)
                .status(Status.PENDING)
                .build();

        service.getCarService().save(car);
        service.getOffMeetingService().save(offMeeting);

        modelAndView.addObject("carDto", service.getCarService().findCarById(carId))
                .addObject("status", "success")
                .addObject("message", "Your meeting request has been sent. Please wait for respond")
                .setViewName("views/user/car-details");
        return modelAndView;
    }

    @GetMapping("/car-detail/{id}")
    public ModelAndView showCarDetails(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("views/user/car-details");
        String check = "false";
        System.out.println("==========================================");
        System.out.println(service.getCarService().findCarEntityById(id).getPost()
                .getClient().getName());
        if(session.getAttribute("client") != null){
            System.out.println(((ClientDto)session.getAttribute("client")).getName());
            if(service.getCarService().findCarEntityById(id).getPost()
                    .getClient().getName().equalsIgnoreCase(((ClientDto)session.getAttribute("client")).getName())){
               check = "true";
            }
        }
        modelAndView.addObject("carDto", service.getCarService().findCarById(id));
        modelAndView.addObject("meetings", service.getOffMeetingService().getOffMeetingsByCarId(id));
        modelAndView.addObject("checkUser", check);
        return modelAndView;


    }

    @GetMapping("/account")
    public ModelAndView showAccountInfoPage() {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            System.out.println(request.getRequestURI());
            return modelAndView;
        }
        stack.push(request.getRequestURI());
        modelAndView.setViewName("views/user/account");
        return modelAndView;
    }

    @GetMapping("/transactions_history")
    public ModelAndView showTransactionsHistoryPage() {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        ClientDto client = (ClientDto) session.getAttribute("client");
        List<InvoiceDto> invoiceList = service.getInvoiceService().findByClient(mapperManager.getClientMapper().toEntity(client));

        modelAndView.addObject("invoiceList", invoiceList)
                .setViewName("views/user/transactions-history");

        return modelAndView;
    }

    @GetMapping("/meeting_history")
    public ModelAndView showMeetingHistoryPage(String errorMsg, String successMsg) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        ClientDto client = (ClientDto) session.getAttribute("client");
        List<OffMeetingDto> meetingList = service.getOffMeetingService().getOffMeetingsByClient(client, PageRequest.of(0, 10));

        modelAndView.addObject("meetingList", meetingList)
                .addObject("errorMsg", errorMsg)
                .addObject("successMsg", successMsg)
                .setViewName("views/user/meeting-history");
        return modelAndView;
    }

    @RequestMapping("/post-history")
    public ModelAndView showPostHistory() {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()){
            return modelAndView;
        }
        ClientDto client = (ClientDto) session.getAttribute("client");
        List<PostDto> postList = service.getPostService().getPostsByClientId(client, PageRequest.of(0,10));

        modelAndView.addObject("postList", postList);

        modelAndView.setViewName("views/user/post-history");
        return modelAndView;
    }

    @RequestMapping("/post-history/delete")
    public ModelAndView deletePost(@RequestParam("id") String postId) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()){
            return modelAndView;
        }

        Post post = service.getPostService().findById(postId);

        if (post == null){
            return showPostHistory();
        }
        List<OffMeeting> offMeetingList = post.getCar().getOffMeetingList();

        offMeetingList.forEach(x -> {
            x.setStatus(Status.DELETED);
            service.sendNotification(x.getClient(),"Your meeting has been cancel due to the car owner post's deletion.");
            service.getOffMeetingService().save(x);

        });

        post.setStatus(Status.DELETED);
        service.getPostService().save(post);


        return showPostHistory();
    }

    @RequestMapping("/post-history/edit-post")
    public ModelAndView showPostEditingPage(@RequestParam("id") String postId){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        Post post = service.getPostService().findById(postId);

        if (post == null){
            return showPostHistory();
        }

        modelAndView.addObject("post", post)
                .setViewName("views/user/edit-post");
        return modelAndView;
    }

    @RequestMapping("/post-history/confirm-edit")
    public ModelAndView editPost(@RequestParam("id") String postId,
                                 @RequestParam("carName") String carName,
                                 @RequestParam("price") String price,
                                 @RequestParam("make") String make,
                                 @RequestParam("model") String model,
//                                 @RequestParam("plan") String plan,
                                 @Nullable @RequestParam("files") MultipartFile[] files,
                                 @Nullable @RequestParam("bodyColor") String bodyColor,
                                 @Nullable @RequestParam("interiorColor") String interiorColor,
                                 @Nullable @RequestParam("interiorMaterial") String interiorMaterial,
                                 @Nullable @RequestParam("body") String body,
                                 @Nullable @RequestParam("licensePlate") String licensePlate,
                                 @Nullable @RequestParam("transmission") String transmission,
                                 @Nullable @RequestParam("seats") String seats,
                                 @Nullable @RequestParam("mileage") String mileage,
                                 @Nullable @RequestParam("engineCapacity") String engineCapacity,
                                 @Nullable @RequestParam("power") String power,
                                 @Nullable @RequestParam("co2Emission") String co2Emission,
                                 @Nullable @RequestParam("fuelType") String fuelType,
                                 @Nullable @RequestParam("firstRegistration") String firstRegistration,
                                 @Nullable @RequestParam("others") String others,
                                 @Nullable @RequestParam("postDescription") String postDescription) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        List<CarImage> carImageList = new ArrayList<>();
        Post post = service.getPostService().findById(postId);
        Car car = post.getCar();

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
                .seats((Objects.equals(seats, "") || seats == null) ? 0 : Integer.parseInt(seats))
                .power(power)
                .engineCapacity(engineCapacity)
                .co2Emission(co2Emission)
                .mileage(mileage)
                .others(others)
                .build();

        if (files != null) {
            for (MultipartFile file : files) {
                CarImage carImage = new CarImage();
                carImage.setContent(service.getStorageService().uploadFile(file));
                carImage.setCar(car);
                carImageList.add(carImage);
            }
        }

        car.getCarImageList().addAll(carImageList);
        car.setName(carName);
        car.setPrice((Objects.equals(price, "")) ? 0 : Long.parseLong(price));
        car.setStatus(Status.AVAILABLE);
        car.setCarDescription(carDescription);

        post.setCar(car);
        post.setDescription(postDescription);

        service.getCarService().save(car);
        service.getPostService().save(post);

        modelAndView.addObject("successMsg", "Your post request has been received! Wait for approval");
        return showPostHistory();
    }



    @GetMapping("/customer_service")
    public ModelAndView showCustomerServicePage() {
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(!isAuthenticated()){
            return modelAndView;
        }
        modelAndView.setViewName("views/user/customer-service");
        return modelAndView ;
    }

    @RequestMapping("/confirm-post/{clientId}")
    public ModelAndView postCar(@PathVariable("clientId") String clientId,
                                @RequestParam("carName") String carName,
                                @RequestParam("price") String price,
                                @RequestParam("make") String make,
                                @RequestParam("model") String model,
                                @RequestParam("plan") String plan,
                                @Nullable @RequestParam("files") MultipartFile[] files,
                                @Nullable @RequestParam("bodyColor") String bodyColor,
                                @Nullable @RequestParam("interiorColor") String interiorColor,
                                @Nullable @RequestParam("interiorMaterial") String interiorMaterial,
                                @Nullable @RequestParam("body") String body,
                                @Nullable @RequestParam("licensePlate") String licensePlate,
                                @Nullable @RequestParam("transmission") String transmission,
                                @Nullable @RequestParam("seats") String seats,
                                @Nullable @RequestParam("mileage") String mileage,
                                @Nullable @RequestParam("engineCapacity") String engineCapacity,
                                @Nullable @RequestParam("power") String power,
                                @Nullable @RequestParam("co2Emission") String co2Emission,
                                @Nullable @RequestParam("fuelType") String fuelType,
                                @Nullable @RequestParam("firstRegistration") String firstRegistration,
                                @Nullable @RequestParam("others") String others,
                                @Nullable @RequestParam("postDescription") String postDescription) {

        ModelAndView modelAndView = new ModelAndView("views/user/post-car");

        Car car = new Car();
        List<CarImage> carImageList = new ArrayList<>();
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
                .seats((Objects.equals(seats, "") || seats == null) ? 0 : Integer.parseInt(seats))
                .power(power)
                .engineCapacity(engineCapacity)
                .co2Emission(co2Emission)
                .mileage(mileage)
                .others(others)
                .build();

        if (files != null) {
            for (MultipartFile file : files) {
                CarImage carImage = new CarImage();
                carImage.setContent(service.getStorageService().uploadFile(file));
                carImage.setCar(car);
                carImageList.add(carImage);
            }
        }

        car.getCarImageList().addAll(carImageList);
        car.setName(carName);
        car.setPrice((Objects.equals(price, "")) ? 0 : Long.parseLong(price));
        car.setStatus(Status.AVAILABLE);
        car.setCarDescription(carDescription);

        Post post = Post.builder()
                .car(car)
                .client(client)
                .postDate(LocalDate.now())
                .postTime(LocalTime.now())
                .status(Status.PENDING)
                .description(postDescription)
                .plan(plan)
                .priority(Plan.getPriority(plan))
                .build();

        service.getCarService().save(car);
        service.getPostService().save(post);

        modelAndView.addObject("successMsg", "Your post request has been received! Wait for approval");
        return modelAndView;
    }



    @RequestMapping("/meeting-history/cancel")
    public ModelAndView cancelMeeting(@RequestParam("id") String id) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        String errorMsg = null;
        String successMsg = null;

        if (!isAuthenticated()) {
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(id);

        if (offMeeting == null) {
            errorMsg = "An error occurred";
            return showMeetingHistoryPage(errorMsg, successMsg);
        }

        if (!offMeeting.getClient().getId().equals(mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client")).getId()) || !offMeeting.getStatus().equals(Status.PENDING)) {
            errorMsg = "You can't to cancel this meeting";
            return showMeetingHistoryPage(errorMsg, successMsg);
        }

        if (offMeeting.getStaff() != null) {
            String msg = "Your meeting with " + offMeeting.getClient() + " at " + offMeeting.getMeetingTime() + ", " + offMeeting.getMeetingDate() + " has been cancelled";
            service.sendNotification(offMeeting.getStaff(), msg);
        }

        service.getOffMeetingService().delete(offMeeting);
        successMsg = "Your meeting has been cancelled";

        return showMeetingHistoryPage(errorMsg, successMsg);
    }

    @RequestMapping("/meeting-history/edit")
    public ModelAndView editMeeting(@RequestParam("offMeetingId") String meetingId,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("description") String description,
                                    @RequestParam("slot") String slot) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        String[] parts = Util.splitDateTimeString(slot);

        OffMeeting meeting = service.getOffMeetingService().findById(meetingId);

        if (meeting == null) {
            String errorMsg = "An error occurred, can't perform this action";
            return showMeetingHistoryPage(errorMsg, null);
        }

        meeting.setMeetingDate(Util.parseLocalDate(parts[0]));
        meeting.setMeetingTime(Util.parseLocalTime(parts[1]));

        if (!phone.isEmpty()) {
            meeting.setPhone(phone);
        }

        if (!description.isEmpty()) {
            meeting.setDescription(description);
        }

        service.getOffMeetingService().save(meeting);

        String successMsg = "Meeting's information has been changed";

        return showMeetingHistoryPage(null, successMsg);
    }

    @GetMapping("/signin")
    public ModelAndView signIn(OAuth2AuthenticationToken token) {
        if (token != null) {
            System.out.println(Objects.requireNonNull(token.getPrincipal().getAttribute("email")).toString());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }

    @RequestMapping("/google-handler")
    public ModelAndView googleHandler(OAuth2AuthenticationToken token) {

        ModelAndView modelAndView = new ModelAndView("views/user/index").addObject("postList", service.getPostService().findPriorPosts());

        String email = token.getPrincipal().getAttribute("email");

        StaffDto staffDto = service.getStaffService().findByEmail(email);

        if (staffDto != null) {
            session.setAttribute("staff", staffDto);
            List<OffMeetingDto> meetingList = service
                    .getOffMeetingService()
                    .findOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED, PageRequest.of(0, 4));
            long lastOffset = service.getOffMeetingService().getLastOffset(staffDto, Status.APPROVED, 4);
            long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED);

            modelAndView.addObject("meetingList", meetingList)
                    .addObject("property", null)
                    .addObject("direction", null)
                    .addObject("offset", 1)
                    .addObject("lastOffset", lastOffset)
                    .addObject("totalMeetings", totalMeetings)

                    .setViewName("views/staff/profile");
            return modelAndView;
        }

        Client client = service.getClientService().findEntityByEmail(email);

        if (client == null) {
            String password = Util.getRandPW();
            client = Client.builder()
                    .email(email)
                    .name(token.getPrincipal().getAttribute("name"))
                    .avatar(token.getPrincipal().getAttribute("picture"))
                    .role("client")
                    .joinDate(LocalDate.now())
                    .password(Util.encodePassword(password))
                    .build();
            service.getClientService().save(client);

            Email clientEmail = new Email();
            clientEmail.setTo(client.getEmail());
            clientEmail.setFrom("timelessride3@gmail.com");
            clientEmail.setSubject("Temporary Password Generated");
            clientEmail.setTemplate("views/email/email-temp-password.html");
            Map<String, Object> properties = new HashMap<>();
            properties.put("tempPassword", password);
            clientEmail.setProperties(properties);
            Runnable runnable = () -> {
                try {
                    service.getEmailService().sendHTMLMessage(clientEmail);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

            };
            new Thread(runnable).start();
        }



        session.setAttribute("client", mapperManager.getClientMapper().toDto(client));
        return modelAndView;
    }

    @RequestMapping("/log-out")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("client");
        modelAndView.setViewName("views/user/index");
        return modelAndView.addObject("postList", service.getPostService().findPriorPosts());
    }

    @RequestMapping("/change-password")
    public ModelAndView changePassword(@RequestParam("oldPass") String oldPass,
                                       @RequestParam("newPass") String newPass) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        Client client = mapperManager.getClientMapper().toEntity((ClientDto) session.getAttribute("client"));

        if (service.getClientService().changePassword(client.getId(), oldPass, newPass)) {
            modelAndView.addObject("status", "success");
            modelAndView.addObject("message", "Successfully updated password");
        } else {
            modelAndView.addObject("status", "fail");
            modelAndView.addObject("message", "Your current password is invalid");
        }

        modelAndView.setViewName("views/user/account");
        return modelAndView;
    }

    @RequestMapping("/update-info")
    public ModelAndView updateAccountInfo(@Nullable @RequestParam("avatar") MultipartFile file,
                                          @RequestParam("name") String name,
                                          @RequestParam("phone") String phone,
                                          @Nullable@RequestParam("gender") String gender,
                                          @Nullable@RequestParam("dob") String dob,
                                          @Nullable@RequestParam("address") String address) {

        ModelAndView modelAndView = new ModelAndView();

        ClientDto clientDto = (ClientDto) session.getAttribute("client");
        Client client = service.getClientService().findEntityById(clientDto.getId());

        String avatar;

        if (file != null) {
            avatar = service.getStorageService().uploadFile(file);
            client.setAvatar(avatar);
        }

        client.setName(name);
        client.setPhone(phone.replaceAll("\\D", ""));
        client.setGender(gender);
        client.setDob(LocalDate.parse(dob));
        client.setAddress(address);

        service.getClientService().save(client);
        session.setAttribute("client", service.getClientService().findById(clientDto.getId()));

        modelAndView.addObject("status","success");
        modelAndView.addObject("message","Your information has been updated successfully");

        modelAndView.setViewName("views/user/account");
        return modelAndView;
    }

    @RequestMapping("/check-notification")
    @ResponseBody
    public List<ClientNotificationDto> checkNotification(@RequestParam("id") String id,
                                                         @Nullable @RequestParam("pages") Integer pages) {

        ClientDto client = service.getClientService().findById(id);

        pages = (pages == null) ? 1 : pages;

        List<ClientNotificationDto> notificationList = service
                .getClientNotificationService()
                .findNotificationsByClient(mapperManager.getClientMapper().toEntity(client), PageRequest.of(0, pages * 10, Sort.by("createDate", "createTime").descending()));

        if (client == null) {
            notificationList = new ArrayList<>();
        }

        return notificationList;
    }
    @RequestMapping("/a12a")
    public ModelAndView a(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/admin/feedback-managerment");
        return modelAndView;

    }
    @RequestMapping( value = "/send_feedback",method = RequestMethod.POST)
    public ModelAndView sendFeedBack(@RequestParam("name") String name,
                             @RequestParam("subject") String subject,
                             @RequestParam("rating") String rating,
                             @RequestParam("message") String message){
        System.out.println(Double.parseDouble(rating));
        Feedback feedback = Feedback.builder()
                .rating(Double.parseDouble(rating))
                .createdAt(LocalDate.now())
                .client(service.getClientService().findEntityById(((ClientDto) session.getAttribute("client")).getId()))
                .description(message).build();


        service.getFeedbackService().save(feedback);
        ModelAndView modelAndView = new ModelAndView("views/user/customer-service");
        modelAndView.addObject("status","success");
        modelAndView.addObject("message","Your feedback has just been sent successfully");
        return modelAndView;

    }

}
