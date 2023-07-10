package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.MyList;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class MappingController {
    @Autowired
    private HttpSession session;
    @Autowired
    private Service service;

    boolean isAuthenticated() {
        return (session.getAttribute("admin") != null);
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }

        Map<String, Long> revenue = service.getPostService().getAnnualRevenue(2023);

        modelAndView.addObject("keys", revenue.keySet())
                .addObject("values", revenue.values())
                .setViewName("views/admin/index");
        return modelAndView;
    }

    @RequestMapping("/mailbox")
    public ModelAndView mailBox() {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/mail-box");
        return modelAndView;
    }

    @RequestMapping("/staff_list")
    public ModelAndView staffList(@Nullable @RequestParam("offset") Integer offset) {

        offset = (offset == null) ? 1 : offset;

        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }

        List<StaffDto> listStaff = service.getStaffService().findAll(PageRequest.of(offset - 1, 10));
        long lastOffset = service.getStaffService().getLastOffset(PageRequest.of(0, 10));
        modelAndView.addObject("offset", offset)
                .addObject("staffList", listStaff)
                .addObject("lastOffset", lastOffset)
                .setViewName("views/admin/staff-list");
        return modelAndView;
    }

    @RequestMapping("/add_new_staff")
    public ModelAndView addStaff() {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/add-new-staff");
        return modelAndView;
    }

    @RequestMapping("/user-list")
    public ModelAndView userList(@Nullable @RequestParam("offset") Integer offset,
                                 MyList<ClientDto> clientList,
                                 boolean isLastPage) {

        offset = (offset == null) ? 1 : offset;
        boolean isFirstPage = offset == 1;

        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }

        if (clientList == null){
            clientList = new MyList<>();
        }

        if(clientList.isEmpty()){
            Pageable pageable = PageRequest.of(offset - 1, 10);
            clientList = new MyList<>();
            clientList.addAll(service.getClientService().findAll(pageable));
            isLastPage = service.getClientService().getLastOffset(pageable) == offset;
        }

        modelAndView.addObject("offset", offset)
                .addObject("clientList", clientList)
                .addObject("isFirstPage", isFirstPage)
                .addObject("isLastPage", isLastPage)
                .setViewName("views/admin/user-list");
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView searchUser(@Nullable @RequestParam Integer offset,
                                   @RequestParam("searchKW") String keyword){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        if (keyword.isBlank()){
            return userList(null, null, false);
        }

        offset = (offset == null) ? 1 : offset;

        Pageable pageable = PageRequest.of(offset - 1, 10);
        MyList<ClientDto> clientList = new MyList<>();
        clientList.addAll(service.getClientService().searchUser(pageable, keyword));

        boolean isLastPage = service.getClientService().getSearchUserLastOffset(pageable, keyword) == offset;

        return userList(offset, clientList, isLastPage);
    }

    @RequestMapping("/add-new-user")
    public ModelAndView showAddNewUserPage(){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        modelAndView.setViewName("views/admin/add-new-user");
        return modelAndView;
    }

    @RequestMapping("/add-new-user/confirm")
    public ModelAndView addUser(@RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @Nullable @RequestParam("phone") String phone,
                                @Nullable @RequestParam("address") String address,
                                @Nullable @RequestParam("gender") String gender,
                                @Nullable @RequestParam("dob") String dob){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        Client client = service.getClientService().findEntityByEmail(email);

        if (client != null){
            return showAddNewUserPage();
        }

        dob = (dob == null) ? "" : dob;

        Client newClient = Client.builder()
                .name(fullName)
                .email(email)
                .password(password)
                .joinDate(LocalDate.now())
                .role("client")
                .phone(phone)
                .gender(gender)
                .address(address)
                .dob(Util.parseLocalDate(dob))
                .build();
        service.getClientService().save(newClient);

        modelAndView.setViewName("views/admin/add-new-user");
        return modelAndView;
    }

    @RequestMapping("/carmanagement")
    public ModelAndView carList(@Nullable @RequestParam("offset") Integer offset) {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        offset = (offset == null) ? 1 : offset;

        List<PostDto> postDto = service.getPostService().getApprovedPostsByStatus(PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
        modelAndView.addObject("postDto", postDto);
        modelAndView.addObject("offset", offset);
        modelAndView.setViewName("views/admin/car-management");
        return modelAndView;
    }

    @RequestMapping("/delete-car/{id}")
    public ModelAndView deleteCar(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }

        Car car = service.getCarService().findCarEntityById(id);
        Post post = service.getPostService().findPostByCarId(id);

        if (car == null) {
            return carList(null);
        }

        if (post == null) {
            return carList(null);
        }

        service.getPostService().delete(post);

        service.getCarService().delete(car);

        return carList(null);
    }

    @RequestMapping("/schedule")
    public ModelAndView scheduleManagement(@RequestParam(name = "date",
                                                        defaultValue = "#{T(java.time.LocalDate).now()}",
                                                        required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        List<OffMeetingDto> meetings = service.getOffMeetingService().getMeetingsByDate(date);

        modelAndView.addObject("meetings", meetings)
                .setViewName("views/admin/schedule-management");
        return modelAndView;
    }

    @RequestMapping("/showroom-list")
    public ModelAndView showroomManagement() {
        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/showroom-list");
        return modelAndView;
    }

    @RequestMapping("/delete-user/{id}")
    public ModelAndView deleteUser(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        Client client = service.getClientService().findEntityById(id);

        if (client == null) {
            return userList(null, null, false);
        }

        service.getClientService().delete(client);

        return userList(null, null, false);
    }

    @RequestMapping("/staff-detail/{staffID}")
    public ModelAndView staffDetail(@PathVariable("staffID") String staffID,
                                    @Nullable @RequestParam("offset") Integer offset) {
        offset = (offset == null) ? 1 : offset;

        ModelAndView modelAndView = new ModelAndView();
        if (!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/staff-profile");
        StaffDto staff = service.getStaffService().findById(staffID);
        List<OffMeetingDto> list = service.getOffMeetingService().findByStaffId(staffID, PageRequest.of(offset - 1, 4));
        modelAndView.addObject("staff", staff);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("offMeetingList", list);

        return modelAndView;
    }

    @RequestMapping("/client-detail/{clientID}")
    public ModelAndView clientDetail(@PathVariable("clientID") String clientID) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if (!isAuthenticated()) {
            return modelAndView;
        }
        ClientDto client = service.getClientService().findById(clientID);
        modelAndView.addObject("client", client)
                .setViewName("views/admin/user-profile");

        return modelAndView;
    }

    @RequestMapping("/edit-user/{id}")
    public ModelAndView editUser(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        Client client = service.getClientService().findEntityById(id);

        if (client == null) {
            return userList(null, null, false);
        }

        modelAndView.addObject("client", client)
                .setViewName("views/admin/edit-user");

        return modelAndView;
    }

    @RequestMapping("/confirm-edit-user")
    public ModelAndView confirmEditUser(@RequestParam("id") String id,
                                        @RequestParam("fullName") String fullName,
                                        @RequestParam("email") String email,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("gender") String gender,
                                        @RequestParam("address") String address) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        Client client = service.getClientService().findEntityById(id);

        client.setName(fullName);
        client.setEmail(email);
        client.setPhone(phone);
        client.setGender(gender);
        client.setAddress(address);

        service.getClientService().save(client);

        return userList(null, null, false);
    }

    @RequestMapping("/edit-staff/{id}")
    public ModelAndView editStaff(@PathVariable String id,
                                  @Nullable @RequestParam("offset") Integer offset) {
        offset = (offset == null) ? 1 : offset;

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        StaffDto staff = service.getStaffService().findById(id);

        if (staff == null) {
            return staffList(null);
        }

        List<OffMeetingDto> list = service.getOffMeetingService().findByStaffId(id, PageRequest.of(offset - 1, 4));

        modelAndView.addObject("staff", staff)
                .addObject("offset", offset)
                .addObject("offMeetingList", list)
                .setViewName("views/admin/edit-staff");
        return modelAndView;
    }

    @RequestMapping("/confirm-edit-staff")
    public ModelAndView confirmEditStaff(@RequestParam("id") String id,
                                         @RequestParam("fullName") String fullName,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("address") String address) {

        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        StaffDto staff = service.getStaffService().findById(id);

        staff.setName(fullName);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setGender(gender);
        staff.setAddress(address);

        service.getStaffService().save(staff);

        return staffList(null);
    }

    @RequestMapping("/delete-staff/{id}")
    public ModelAndView deleteStaff(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if (!isAuthenticated()) {
            return modelAndView;
        }

        StaffDto staff = service.getStaffService().findById(id);

        if (staff == null) {
            return staffList(null);
        }

        service.getStaffService().delete(staff);

        return staffList(null);
    }

    @RequestMapping("/feedbacks")
    public ModelAndView showFeedbacks(@Nullable @RequestParam("offset") Integer offset) {
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        offset = (offset == null) ? 1 : offset;

        if (!isAuthenticated()) {
            return modelAndView;
        }

        List<FeedbackDto> feedbackList = service.getFeedbackService().findFeedbacksPerPage(PageRequest.of(offset - 1, 10));

        modelAndView.addObject("feedbackList", feedbackList)
                .addObject("offset", offset)
                .setViewName("views/admin/feed-back");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("admin");
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }


}
