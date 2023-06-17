package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController {
    @Autowired
    private Service service;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;
}
