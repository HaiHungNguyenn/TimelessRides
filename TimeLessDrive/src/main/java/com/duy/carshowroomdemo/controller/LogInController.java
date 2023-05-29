package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.AdminDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
        }

        else if(clientDto != null){
            modelAndView.setViewName("views/user/index");
            session.setAttribute("user", clientDto);
        }

        else if(adminDto != null){
            modelAndView.setViewName("views/admin/index");
            session.setAttribute("user", adminDto);
        }

        else {
            modelAndView.setViewName("views/user/login");
        }
        return modelAndView;

    }
    @RequestMapping("/regis-form")
    public ModelAndView register(@RequestParam("email") String email, @RequestParam("password") String password){
            ModelAndView modelAndView = new ModelAndView();


            if(service.getClientService().isExist(email)){

                modelAndView.addObject("loginMess","Your account is already regitered");
                modelAndView.setViewName("views/user/login");

            }else {
                Client client = new Client();
                client.setEmail(email);
                client.setPassword(Util.encodePassword(password));
                client.setJoinDate(LocalDate.now());
                service.getClientService().save(client);
                modelAndView.setViewName("views/user/index");
            }
            return modelAndView;
    }


}
