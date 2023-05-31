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
    @RequestMapping("/abb")
    public ClientDto findClientByID(@RequestParam("id") String id){
        return service.getClientService().findById(id);
    }
//    @RequestMapping("/zzz")
//    public Boolean deleteClientById(@RequestParam("id") String id){
//        return service.getClientService().deletebyId(id);
//    }
    @RequestMapping("/aaa")
    public boolean changeClientPassword(@RequestParam("id") String id,@RequestParam("oldPassword") String oldPass,@RequestParam("newPassword") String newPass){
        return service.getClientService().changePassword(id,oldPass,newPass);
    }


}
