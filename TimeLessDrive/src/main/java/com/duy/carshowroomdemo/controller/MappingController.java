package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class MappingController {
    @Autowired
    private HttpSession session;
    @Autowired
    private Service service;

    boolean isAuthenticated(){
        return (session.getAttribute("admin") != null);
    }

    @RequestMapping("/admin/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/index");
        return modelAndView;
    }

    @RequestMapping("/admin/mailbox")
    public ModelAndView mailBox(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/mail-box");
        return modelAndView;
    }
    @RequestMapping("/admin/staff_list")
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
    @RequestMapping("/admin/add_new_staff")
    public ModelAndView addStaff(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/add-new-staff");
        return modelAndView;
    }

    @RequestMapping("/admin/user-list")
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

    @RequestMapping("/admim/carmanagement")
    public ModelAndView carManagement(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/car-management");
        return modelAndView;
    }
    @RequestMapping("/admin/schedule")
    public ModelAndView scheduleManagement(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/schedule-management");
        return modelAndView;
    }

    @RequestMapping("/admin/showroom-list")
    public ModelAndView showroomManagement(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/showroom-list");
        return modelAndView;
    }




    @RequestMapping("/admin/staff-detail/{staffID}")
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
    @RequestMapping("/admin/client-detail/{clientID}")
    public ModelAndView clientDetail(@PathVariable("clientID") String clientID){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/user-profile");
        ClientDto client = service.getClientService().findById(clientID);
        modelAndView.addObject("client",client);

        return modelAndView;
    }
    @RequestMapping("/admin/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        session.removeAttribute("admin");
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }


}
