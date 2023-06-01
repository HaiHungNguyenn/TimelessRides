package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Stack;

@Controller
public class UserController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    public final Stack<String> stack = new Stack<>();
    public boolean isAuthenticated(){
        return(session.getAttribute("client")!=null);
    }

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
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/account");
        stack.push(request.getRequestURI());
        return modelAndView;

    }
    @GetMapping ("/transactions_history")
    public ModelAndView transactionsHistory(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/transactions-history");
        return modelAndView;
    }
    @GetMapping ("/meeting_history")
    public ModelAndView meetingHistory(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
        modelAndView.setViewName("views/user/meeting-history");
        return modelAndView;
    }
    @GetMapping ("/car-detail")
    public ModelAndView carDetail(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/user/car-details");
        return modelAndView;
    }
    @GetMapping ("/post_car")
    public ModelAndView postCar(){
        ModelAndView modelAndView = new ModelAndView();
        if(!isAuthenticated()) {
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }
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
    public ModelAndView signIn(OAuth2AuthenticationToken token){
        if(token != null){
            System.out.println(token.getPrincipal().getAttribute("email").toString());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }

    @RequestMapping("/google-handler")
    public ModelAndView googleHandler(OAuth2AuthenticationToken token){


            System.out.println(token.getPrincipal().getAttribute("email").toString());
            token.getPrincipal().getAttributes().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });


        return home();
    }
    //    change password

}
