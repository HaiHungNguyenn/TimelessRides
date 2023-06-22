package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    // mapping







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

    @RequestMapping("/aaa")
    public boolean changeClientPassword(@RequestParam("id") String id,@RequestParam("oldPassword") String oldPass,@RequestParam("newPassword") String newPass){
        return service.getClientService().changePassword(id,oldPass,newPass);
    }
    @RequestMapping("/bbb")
    public boolean changeStaffPassword(@RequestParam("id") String id,@RequestParam("oldPassword") String oldPass,@RequestParam("newPassword") String newPass){
        return service.getStaffService().changePassword(id,oldPass,newPass);
    }
    @RequestMapping("/xxx")
    public List<CarDto> getCarList(){
        List<CarDto> list = service.getCarService().getCarList();
        return list;
    }
    @RequestMapping("/add-new-staff")
    public ModelAndView createNewStaff(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("address") String address,
                                       @RequestParam("password") String password){
        ModelAndView modelAndView = new ModelAndView("views/admin/add-new-staff");

        if(service.getStaffService().findByEmail(email) != null
                || service.getClientService().findByEmail(email) != null
                || service.getAdminService().findByEmail(email) != null) {
            modelAndView.addObject("message","This email has been registered already");
            modelAndView.addObject("status","fail");
            return modelAndView;
        }


        Staff staff = new Staff();
        staff.setName(name);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setAddress(address);
        staff.setPassword(Util.encodePassword(password));
        staff.setRole("staff");
        staff.setJoinDate(LocalDate.now());
        service.getStaffService().save(staff);

        modelAndView.addObject("message","Successfully added");
        modelAndView.addObject("status","success");


        return modelAndView;
    }

}
