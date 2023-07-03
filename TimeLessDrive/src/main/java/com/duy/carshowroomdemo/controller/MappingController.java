package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.repository.CarRepository;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class MappingController {
    @Autowired
    private HttpSession session;
    @Autowired
    private Service service;

    boolean isAuthenticated(){
        return (session.getAttribute("admin") != null);
    }

    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/index");
        return modelAndView;
    }

    @RequestMapping("/mailbox")
    public ModelAndView mailBox(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/mail-box");
        return modelAndView;
    }
    @RequestMapping("/staff_list")
    public ModelAndView staffList(@Nullable @RequestParam("offset") Integer offset){

        offset = (offset == null) ? 1: offset;

        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        List<StaffDto> listStaff = new ArrayList<>();
        listStaff = service.getStaffService().findAll( PageRequest.of(offset - 1, 10));
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("staffList", listStaff);
        modelAndView.setViewName("views/admin/staff-list");
        return modelAndView;
    }
    @RequestMapping("/add_new_staff")
    public ModelAndView addStaff(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/add-new-staff");
        return modelAndView;
    }

    @RequestMapping("/user-list")
    public ModelAndView userList(@Nullable @RequestParam("offset") Integer offset){

        offset = (offset == null) ? 1: offset;

        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }

        List<ClientDto> clientList = new ArrayList<>();
        clientList = service.getClientService().findAll( PageRequest.of(offset - 1, 10));

        modelAndView.setViewName("views/admin/user-list");
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("clientList", clientList);
        return modelAndView;
    }

    @RequestMapping("/carmanagement")
    public ModelAndView carList(@Nullable @RequestParam("offset") Integer offset ){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        offset = (offset == null) ? 1 : offset;

        List<PostDto> postDto = service.getPostService().getApprovedPostsByStatus(PageRequest.of(offset - 1, 9, Sort.by("priority").descending()));
        modelAndView.addObject("postDto",postDto);
        modelAndView.addObject("offset",offset);
        modelAndView.setViewName("views/admin/car-management");
        return modelAndView;
    }

    @RequestMapping("/delete-car/{id}")
    public ModelAndView deleteCar(@PathVariable String id ) {
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }

        Car car = service.getCarService().findCarEntityById(id);
        Post post = service.getPostService().findPostByCarId(id);

        if (car == null){
            return carList(null);
        }

        if(post == null){
            return carList(null);
        }

        service.getPostService().delete(post);

        service.getCarService().delete(car);

        return carList(null);
    }

    @RequestMapping("/schedule")
    public ModelAndView scheduleManagement(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/schedule-management");
        return modelAndView;
    }

    @RequestMapping("/showroom-list")
    public ModelAndView showroomManagement(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/showroom-list");
        return modelAndView;
    }

    @RequestMapping("/delete-user/{id}")
    public ModelAndView deleteUser(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        Client client = service.getClientService().findEntityById(id);

        if (client == null){
            return userList(null);
        }

        service.getClientService().delete(client);

        return userList(null);
    }

    @RequestMapping("/staff-detail/{staffID}")
    public ModelAndView staffDetail(@PathVariable("staffID") String staffID,
                                    @Nullable @RequestParam("offset") Integer offset){
        offset = (offset == null) ? 1: offset;

        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/staff-profile");
        StaffDto staff = service.getStaffService().findById(staffID);
        List<OffMeetingDto> list = service.getOffMeetingService().findByStaffId(staffID,PageRequest.of(offset -1,4));
        modelAndView.addObject("staff",staff);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("offMeetingList",list);

        return modelAndView;
    }
    @RequestMapping("/client-detail/{clientID}")
    public ModelAndView clientDetail(@PathVariable("clientID") String clientID){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(!isAuthenticated()){
            return modelAndView;
        }
        ClientDto client = service.getClientService().findById(clientID);
        modelAndView.addObject("client",client)
            .setViewName("views/admin/user-profile");

        return modelAndView;
    }

    @RequestMapping("/feedbacks")
    public ModelAndView showFeedbacks(@Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        offset = (offset == null) ? 1 : offset;

        if (!isAuthenticated()){
            return modelAndView;
        }

        List<FeedbackDto> feedbackList = service.getFeedbackService().findFeedbacksPerPage(PageRequest.of(offset - 1, 10));

        modelAndView.addObject("feedbackList", feedbackList)
                .addObject("offset", offset)
                .setViewName("views/admin/feed-back");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("admin");
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }


}
