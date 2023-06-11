package com.duy.carshowroomdemo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MappingController {
    @Autowired
    private HttpSession session;

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
    public ModelAndView staffList(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
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
    public ModelAndView userList(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/user-list");
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




    @RequestMapping("/admin/staff-detail")
    public ModelAndView staffDetail(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/staff-profile");
        return modelAndView;
    }
    @RequestMapping("/admin/client-detail")
    public ModelAndView clientDetail(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
        }
        modelAndView.setViewName("views/admin/user-profile");
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
