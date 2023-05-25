package com.example.timelessdrive_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping ("/")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping ("/car")
    public ModelAndView car(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("car");
        return modelAndView;
    }
    @GetMapping ("/account")
    public ModelAndView account(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("account");
        return modelAndView;
    }
    @GetMapping ("/transactions_history")
    public ModelAndView transactionsHistory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("transactions_history");
        return modelAndView;
    }
    @GetMapping ("/meeting_history")
    public ModelAndView meetingHistory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting_history");
        return modelAndView;
    }
    @GetMapping ("/post_car")
    public ModelAndView postCar(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("post_car");
        return modelAndView;
    }
    @GetMapping ("/customer_service")
    public ModelAndView customerService(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer_service");
        return modelAndView;
    }
    @GetMapping ("/signin")
    public ModelAndView signin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
