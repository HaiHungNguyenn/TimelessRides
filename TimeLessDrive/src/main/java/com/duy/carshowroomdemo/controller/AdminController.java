package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    // chua co url
    @RequestMapping("/abc")
    public List<ClientDto> clientList(){
        List<ClientDto> list = service.getClientService().getClientList();
        return list;
    }
    @RequestMapping("/xyz")
    public List<ClientDto> clientListByJoinDate(@RequestParam(name="startDate") String startDate , @RequestParam(name="endDate") String endDate){
        return service.getClientService().listByJoinDate(startDate,endDate);
    }

}
