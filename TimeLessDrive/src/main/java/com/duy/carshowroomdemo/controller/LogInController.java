package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LogInController {
    @Autowired
    private Service service;

    @GetMapping("/login-form")
    ModelAndView access(@RequestParam String email, @RequestParam String password){
       ModelAndView modelAndView = new ModelAndView();
        StaffDto staffDto = service.getStaffService().login(email, password);
        ClientDto clientDto = service.getClientService().login(email, password);
        AdminDto adminDto = service.getAdminService().login(email,password);
        if(staffDto != null){
            modelAndView.setViewName("views/staff/profile");
        }

        if(clientDto != null){
            modelAndView.setViewName("/");
        }

        if(adminDto != null){
            modelAndView.setViewName("views/admin/index");
        }

        else {
            modelAndView.setViewName("signin");
        }
        return modelAndView;

    }
}
