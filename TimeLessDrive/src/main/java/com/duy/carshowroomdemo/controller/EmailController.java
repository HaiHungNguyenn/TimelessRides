package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Email;
import com.duy.carshowroomdemo.service.EmailService;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmailController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    @Autowired
    private EmailService emailService;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;
    @RequestMapping("/send-verifycode")
    public ModelAndView sendCode() throws MessagingException {
        System.out.println("hello");
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        modelAndView.addObject("popup",true);
        Email email = new Email();
        String code = Util.generateRandomString();
        email.setTo("hainhse173100@fpt.edu.vn");
        email.setFrom("nguyenhai181911@gmail.com");
        email.setSubject("Email Verification Code");
        email.setTemplate("views/email/email-verify.html");
        Map<String, Object> properties = new HashMap<>();
        properties.put("verificationCode", code);
        email.setProperties(properties);
        System.out.println(email.getProperties());
        emailService.sendHTMLMessage(email);
        session.setAttribute("verificationCode",code);
        return modelAndView;
    }
    @RequestMapping("/check-verifycode")
    public ModelAndView checkCode(@RequestParam("code") String code){
        System.out.println("check code here ------");
        String veriCode = (String) session.getAttribute("verificationCode");
        System.out.println(veriCode);
        System.out.println(code);
        if(code.equalsIgnoreCase(veriCode)){
            System.out.println("Successfull");
        }else {
            System.out.println("fail");
        }
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        return modelAndView;

    }
}
