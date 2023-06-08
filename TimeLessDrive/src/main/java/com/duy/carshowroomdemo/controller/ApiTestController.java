package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
@RestController
public class ApiTestController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    // mapping
    @RequestMapping("hel")
    public String he(){
        return "ashdakj";
    }


}
