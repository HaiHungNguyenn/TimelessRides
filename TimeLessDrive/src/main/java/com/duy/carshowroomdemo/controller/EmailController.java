package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;
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
    public ModelAndView sendCode(@RequestParam("email") String userEmail) throws MessagingException {
        Client client = service.getClientService().findEntityByEmail("userEmail");

        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(client == null){
            modelAndView.addObject("loginMsg","your email has not been registered");
            return modelAndView;
        }
        modelAndView.addObject("popup_first",true);
        modelAndView.addObject("email",userEmail);
        session.setAttribute("email",userEmail);
        Email email = new Email();
        String code = Util.generateRandomString();
        email.setTo("hainhse173100@fpt.edu.vn");
//        email.setTo(userEmail);
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
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        if(code.equalsIgnoreCase(veriCode)){
            System.out.println("Successfull");
            modelAndView.addObject("popup_second",true);
            modelAndView.addObject("msg","please enter your new password");

        }else {
            System.out.println("Fail");
            modelAndView.addObject("popup_first",true);
            modelAndView.addObject("msg","wrong code");
        }
        return modelAndView;

    }

    @RequestMapping("/renew-password")
    public ModelAndView updatePassword(@RequestParam("newPassword") String newPassword){
        System.out.println("renew here------");
        ModelAndView modelAndView = new ModelAndView();
        String userEmail = (String)session.getAttribute("email");
        Client client = service.getClientService().findEntityByEmail(userEmail);
        if(client != null){
            client.setPassword(Util.encodePassword(newPassword));
            service.getClientService().save(client);
            modelAndView.addObject("loginMsg","new password has been updated");
        }
        modelAndView.setViewName("views/user/login");
        return modelAndView;
    }
}
