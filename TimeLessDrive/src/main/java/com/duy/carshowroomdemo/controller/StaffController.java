package com.duy.carshowroomdemo.controller;

//import com.duy.carshowroomdemo.services.Service;
import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Invoice;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.OffMeetingRepository;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    private final MapperManager mapperManager = new MapperManager();
    @Autowired
    private OffMeetingRepository offMeetingRepository;

    public boolean isAuthenticated(){
        return (session.getAttribute("staff") != null);
    }

    @RequestMapping("/staff/")
    public ModelAndView showLoginPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/staff/my-index");

        return modelAndView;
    }

    @RequestMapping("/staff/profile")
    public ModelAndView loginWithEmail(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        StaffDto staff = service.getStaffService().login(email, password);
        if(staff != null){
            System.out.println("the staff is not null");
        }
        if(staff != null){
            session.setAttribute("staff", staff);
            modelAndView.setViewName("views/staff/profile");
        }else{
            modelAndView.setViewName("views/staff/my-index");
        }

        return modelAndView;
    }

    @RequestMapping("/home")
    public ModelAndView showHomePage(String direction,
                                     String property,
                                     @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        StaffDto staff = (StaffDto) session.getAttribute("staff");

        offset = (offset == null) ? 1 : offset;

        List<OffMeetingDto> meetingList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.APPROVED, PageRequest.of(offset - 1, 4, Sort.by(sortDirection, property)));
        }else {
            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.APPROVED, PageRequest.of(offset - 1, 4));
        }

        long lastOffset = service.getOffMeetingService().getLastOffset(staff, Status.APPROVED, 4);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staff, Status.APPROVED);

        modelAndView.addObject("meetingList", meetingList);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.addObject("totalMeetings", totalMeetings);
        modelAndView.setViewName("views/staff/profile");

        return modelAndView;
    }

    @RequestMapping("/home/sorted-by-{property}-{direction}")
    public ModelAndView showHomePageSortedNext(@RequestParam("offset") int offset,
                                               @PathVariable String property,
                                               @PathVariable String direction){
        return showHomePage(direction, property, offset);
    }

    @RequestMapping("/home/action={action}")
    public ModelAndView showHomePageAction(@PathVariable String action,
                                           @RequestParam("meetingId") String id,
                                           @RequestParam("property") String property,
                                           @RequestParam("direction") String direction,
                                           @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(id);

        if(offMeeting == null){
            return modelAndView;
        }

        if(action.equalsIgnoreCase("fail")){
            offMeeting.setStatus(Status.FAILED);
        }else if(action.equalsIgnoreCase("succeed")){
            offMeeting.setStatus(Status.SUCCESS);
        }

        service.getOffMeetingService().save(offMeeting);

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showHomePage(direction, property, offset);
    }


    @RequestMapping("/meeting-requests")
    public ModelAndView showMeetingRequestList(String direction,
                                               String property,
                                               @Nullable @RequestParam("page") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        List<OffMeetingDto> offMeetingList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            offMeetingList = service.getOffMeetingService().getOffMeetingsSortedPerPage(PageRequest.of(offset - 1, 10, Sort.by(sortDirection, property)));
        }else {
            offMeetingList = service.getOffMeetingService().getOffMeetingsPerPage(PageRequest.of(offset - 1, 10));
        }

        long totalOffMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus();
        long lastOffset = service.getOffMeetingService().getLastOffset(10);

        modelAndView.addObject("offMeetingList", offMeetingList);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("totalOffMeetings", totalOffMeetings);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.setViewName("views/staff/meeting-req");

        return modelAndView;
    }

    @RequestMapping("/meeting-requests/sorted-by-{property}-{direction}")
    public ModelAndView showMeetingRequestsSortedPerPage1(@PathVariable String property,
                                                          @PathVariable String direction,
                                                          @Nullable @RequestParam("page") Integer offset){
        return showMeetingRequestList(direction, property, offset);
    }

    @RequestMapping("/meeting-requests/action={action}")
    public ModelAndView respondMeetingRequest(@PathVariable String action,
                                              @RequestParam("id") String meetingId,
                                              @RequestParam("property") String property,
                                              @RequestParam("direction") String direction,
                                              @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(meetingId);

        if(offMeeting == null){
            return showPostRequestList(direction,property,offset);
        }

        if(offMeeting.getStaff() != null){
            System.out.println("This request has been reviewed by someone else");
        }else {
            offMeeting.setStaff(mapperManager.getStaffMapper().toEntity((StaffDto) session.getAttribute("staff")));
            offMeeting.setStatus(action.equalsIgnoreCase("decline") ? Status.DECLINED : Status.APPROVED);
        }

        service.getOffMeetingService().save(offMeeting);

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showMeetingRequestList(direction,property,offset);
    }

    @RequestMapping("/post-requests")
    public ModelAndView showPostRequestList(String direction,
                                            String property,
                                            @Nullable @RequestParam("page") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        List<PostDto> postRequestList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            postRequestList = service.getPostService().getPostSortedPerPage(PageRequest.of(offset - 1, 10, Sort.by(sortDirection, property)));
        }else {
            postRequestList = service.getPostService().getPostsPerPage(PageRequest.of(offset - 1, 10));
        }

        long totalPostRequests = service.getPostService().getTotalPostRequests();
        long lastOffset = service.getPostService().getLastOffset(10);

        modelAndView.addObject("offset", offset);
        modelAndView.addObject("postRequestList", postRequestList);
        modelAndView.addObject("totalPostRequests", totalPostRequests);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("property", property);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.setViewName("views/staff/post-req");

        return modelAndView;
    }

    @RequestMapping("/post-requests/sorted-by-{property}-{direction}")
    public ModelAndView showPostRequestSortedPerPage(@PathVariable String direction,
                                                     @PathVariable String property,
                                                     @Nullable @RequestParam("page") Integer offset){
        return showPostRequestList(direction,property, offset);
    }

    @RequestMapping("/post-requests/action={action}")
    public ModelAndView respondPostRequest(@PathVariable String action,
                                           @RequestParam("id") String id,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("direction") String direction,
                                           @RequestParam("property") String property){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        Post post = service.getPostService().findById(id);

        if(post == null){
            return showPostRequestList(direction,property,offset);
        }else if(!post.getStatus().equalsIgnoreCase(Status.PENDING)){
            return showPostRequestList(direction,property,offset);
        }

        if(action.equalsIgnoreCase("decline")){
            post.setStatus(Status.DECLINED);
        }else {
            post.setStatus(Status.APPROVED);
        }

        service.getPostService().save(post);

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showPostRequestList(direction,property,offset);
    }

    @RequestMapping("/user-details/{id}")
    public ModelAndView viewUserDetails(@PathVariable String id,
                                        @Nullable @RequestParam("mOffset") Integer mOffset,
                                        @Nullable @RequestParam("pOffset") Integer pOffset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        mOffset = (mOffset == null) ? 1 : mOffset;
        pOffset = (pOffset == null) ? 1 : pOffset;

        ClientDto client = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(client, PageRequest.of(mOffset - 1, 3));
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(client, PageRequest.of(pOffset - 1, 3));
        long lastMOffset = service.getOffMeetingService().getLastOffset(client, 3);
        long lastPOffset = service.getPostService().getLastOffset(client, 3);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByClient(client);
        long totalPosts = service.getPostService().getTotalPostRequests(client);
        modelAndView.addObject("client", client);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.addObject("totalMeetings", totalMeetings);
        modelAndView.addObject("totalPosts", totalPosts);
        modelAndView.addObject("mOffset", mOffset);
        modelAndView.addObject("pOffset", pOffset);
        modelAndView.addObject("lastMOffset", lastMOffset);
        modelAndView.addObject("lastPOffset", lastPOffset);
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/create-invoice")
    public ModelAndView showCreateInvoicePage(@Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        StaffDto staff = (StaffDto) session.getAttribute("staff");
        List<OffMeetingDto> offMeetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.SUCCESS, PageRequest.of(offset - 1, 5));
        long lastOffset = service.getOffMeetingService().getLastOffset(staff, Status.SUCCESS, 5);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staff, Status.SUCCESS);

        modelAndView.addObject("offMeetingList", offMeetingList);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("totalMeetings", totalMeetings);
        modelAndView.setViewName("views/staff/create-invoice-test");

        return modelAndView;
    }

    @RequestMapping("/create-invoice/create/{id}")
    public ModelAndView createInvoiceConfirm(@PathVariable String id,
                                             @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting meeting = service.getOffMeetingService().findById(id);

        Invoice invoice = new Invoice();
        invoice.setClient(meeting.getClient());
        invoice.setStaff(mapperManager.getStaffMapper().toEntity((StaffDto) session.getAttribute("staff")));
        invoice.setCar(meeting.getCar());
        invoice.setCreateDate(LocalDate.now());
        invoice.setCreateTime(LocalTime.now());
        invoice.setStatus(Status.PAID);
        invoice.setTax("10%");
        invoice.setTotal(Util.calculateTotal(meeting.getCar().getPrice(), invoice.getTax()));
        invoice.setOtherInformation(Util.getRandText(10));

        service.getInvoiceService().save(invoice);

        meeting.setStatus(Status.DONE);

        service.getOffMeetingService().save(meeting);


        return showCreateInvoicePage(offset);

    }

    @RequestMapping("/create-invoice/create")
    public ModelAndView createInvoice(@RequestParam("fullName") String fullName,
                                      @RequestParam("email") String email,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("carName") String carName,
                                      @RequestParam("brand") String brand,
                                      @RequestParam("boughtYear") int boughtYear,
                                      @RequestParam("licensePlate") String licensePlate,
                                      @RequestParam("additionalInfo") String additionalInfo){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/staff/create-invoice");

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        ClientDto client = service.getClientService().getClientByEmailAndNameAndPhone(email, fullName, phone);

        if(client == null){
            modelAndView.addObject("errorMsg", "Client not found");
            modelAndView.addObject("fullName", fullName);
            modelAndView.addObject("email", email);
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("carName", carName);
            modelAndView.addObject("brand", brand);
            modelAndView.addObject("boughtYear", boughtYear);
            modelAndView.addObject("licensePlate", licensePlate);
            modelAndView.addObject("additionalInfo", additionalInfo);
            return modelAndView;
        }

//        CarDto car = service.getCarService().findCarByNameAndBrandAndBoughtYearAndLicensePlate(carName, brand, boughtYear, licensePlate);
        CarDto car = new CarDto();

        if(car == null){
            modelAndView.addObject("errorMsg", "Car not found");
            modelAndView.addObject("fullName", fullName);
            modelAndView.addObject("email", email);
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("carName", carName);
            modelAndView.addObject("brand", brand);
            modelAndView.addObject("boughtYear", boughtYear);
            modelAndView.addObject("licensePlate", licensePlate);
            modelAndView.addObject("additionalInfo", additionalInfo);
            return modelAndView;
        }

        boolean isSavedInvoice = service.getInvoiceService().createNewInvoice(client, (StaffDto) session.getAttribute("staff"), car, additionalInfo);
        if(isSavedInvoice){
            modelAndView.addObject("successMsg", "Invoice created successfully");
        }else {
            modelAndView.addObject("errorMsg", "An error occurred");
        }

        return modelAndView;
    }

    @RequestMapping("/log-out")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();

        session.removeAttribute("staff");
        modelAndView.setViewName("views/user/index");

        return modelAndView;
    }

    @RequestMapping("/car-details/{id}")
    public ModelAndView showCarDetails(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        CarDto car = service.getCarService().findCarById(id);
        modelAndView.addObject("car", car);
        modelAndView.setViewName("views/staff/car-details");

        return modelAndView;
    }


    // change password
    @RequestMapping("/bb")
    public boolean changeStaffPassword1(@RequestParam("id") String id,@RequestParam("oldPassword") String oldPass,@RequestParam("newPassword") String newPass){
        return service.getStaffService().changePassword(id,oldPass,newPass);
    }

}
