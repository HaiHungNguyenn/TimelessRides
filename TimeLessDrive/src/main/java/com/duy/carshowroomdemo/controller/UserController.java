package com.duy.carshowroomdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @GetMapping ("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/index");
        return modelAndView;
    }
    @GetMapping ("/car")
    public ModelAndView car(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/car");
        return modelAndView;
    }
    @GetMapping ("/account")
    public ModelAndView account(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/account");
        return modelAndView;
    }
    @GetMapping ("/transactions_history")
    public ModelAndView transactionsHistory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/transactions-history");
        return modelAndView;
    }
    @GetMapping ("/meeting_history")
    public ModelAndView meetingHistory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/meeting-history");
        return modelAndView;
    }
    @GetMapping ("/post_car")
    public ModelAndView postCar(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/post-car");
        return modelAndView;
    }
    @GetMapping ("/customer_service")
    public ModelAndView customerService(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/customer-service");
        return modelAndView;
    }
    @GetMapping ("/signin")
    public ModelAndView signIn(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }
}
