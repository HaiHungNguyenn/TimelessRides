package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class LogInController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;

    @RequestMapping("/login-form")
    ModelAndView access(@RequestParam("email") String email, @RequestParam("password") String password){
       ModelAndView modelAndView = new ModelAndView();
        StaffDto staffDto = service.getStaffService().login(email, password);
        ClientDto clientDto = service.getClientService().login(email, password);
        AdminDto adminDto = service.getAdminService().login(email,password);
        if(staffDto != null){
            modelAndView.setViewName("views/staff/profile");
            session.setAttribute("staff", staffDto);

            List<OffMeetingDto> meetingList;

            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED, PageRequest.of(0, 4));
            long lastOffset = service.getOffMeetingService().getLastOffset(staffDto, Status.APPROVED, 4);
            long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED);
            modelAndView.addObject("meetingList", meetingList);
            modelAndView.addObject("property", null);
            modelAndView.addObject("direction", null);
            modelAndView.addObject("offset", 1);
            modelAndView.addObject("lastOffset", lastOffset);
            modelAndView.addObject("totalMeetings", totalMeetings);
        }

        else if(clientDto != null){
            modelAndView.setViewName("views/user/index");
            session.setAttribute("client", clientDto);
        }

        else if(adminDto != null){
            modelAndView.setViewName("views/admin/index");
            session.setAttribute("admin", adminDto);
        }

        else {
            modelAndView.setViewName("views/user/login");
        }
        return modelAndView;

    }
    @RequestMapping(value = "/register-form", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("email") String email, @RequestParam("password") String password,@RequestParam("name") String name){
            ModelAndView modelAndView = new ModelAndView();


            if(service.getClientService().isExist(email) ||service.getStaffService().isExist(email)||service.getAdminService().isExist(email)){

                modelAndView.addObject("loginMess","This email is already registered");
                modelAndView.setViewName("views/user/login");

            }else {
                Client client = new Client();
                client.setName(name);
                client.setEmail(email);
                client.setPassword(Util.encodePassword(password));
                client.setJoinDate(LocalDate.now());
                service.getClientService().save(client);
                modelAndView.addObject("loginMess","Successfully registered");
                modelAndView.setViewName("views/user/login");
            }
            return modelAndView;
    }



}
