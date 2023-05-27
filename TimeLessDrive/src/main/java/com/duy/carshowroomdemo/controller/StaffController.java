package com.duy.carshowroomdemo.controller;

//import com.duy.carshowroomdemo.services.Service;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StaffController {

    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;

    @RequestMapping("/staff/")
    public ModelAndView showLoginPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/staff/my-index");

        return modelAndView;
    }

    @RequestMapping("/staff/profile")
    public ModelAndView loginWithEmail(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        StaffDto staff = service.getStaffService().login(email, password);
        if(staff != null){
            System.out.println("the staff is not null");
        }
        if(staff != null){
            session.setAttribute("staff", staff);
            modelAndView.setViewName("views/staff/profile");
//            modelAndView.addObject("staff", staff);
        }else{
            modelAndView.setViewName("views/staff/my-index");
        }

        return modelAndView;
    }

    @RequestMapping("/staff/home")
    public ModelAndView showHomePage(){
        ModelAndView modelAndView = new ModelAndView();

        if(session.getAttribute("user") == null){
            modelAndView.setViewName("views/staff/my-index");
        }else{
            modelAndView.setViewName("views/staff/profile");
        }

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests")
    public ModelAndView showMeetingRequestList(){
        ModelAndView modelAndView = new ModelAndView();

        List<OffMeetingDto> allOffMeetings = service.getOffMeetingService().getOffMeetingsPerPage(1, 10);
        modelAndView.addObject("allOffMeetings", allOffMeetings);
        modelAndView.addObject("currentPage", 1);
        modelAndView.setViewName("views/staff/meeting-req");

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests/{offSet}")
    public ModelAndView showMeetingRequestsPerPage(@PathVariable int offSet){
        ModelAndView modelAndView = new ModelAndView();

        List<OffMeetingDto> allOffMeetings = service.getOffMeetingService().getOffMeetingsPerPage(offSet, 10);
        modelAndView.addObject("allOffMeetings", allOffMeetings);
        modelAndView.addObject("currentPage", offSet);
        modelAndView.setViewName("views/staff/meeting-req");

        return modelAndView;
    }

    @RequestMapping("/staff/post-requests")
    public ModelAndView showPostRequestList(){
        ModelAndView modelAndView = new ModelAndView();
        List<PostDto> allPostRequests = service.getPostService().getPostsPerPage(1, 10);
        modelAndView.addObject("currentPage", 1);
        modelAndView.addObject("allPostRequests", allPostRequests);
        modelAndView.setViewName("views/staff/post-req");

        return modelAndView;
    }

    @RequestMapping("/staff/post-requests/{offSet}")
    public ModelAndView showPostRequestsPerPage(@PathVariable int offSet){
        ModelAndView modelAndView = new ModelAndView();

        List<PostDto> allPostRequests = service.getPostService().getPostsPerPage(offSet, 10);
        modelAndView.addObject("allPostRequests", allPostRequests);
        modelAndView.addObject("currentPage", offSet);
        modelAndView.setViewName("views/staff/post-req");

        return modelAndView;
    }

    @RequestMapping("/staff/create-invoice")
    public ModelAndView createInvoice(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/staff/create-invoice");

        return modelAndView;
    }

    @RequestMapping("/staff/user-details/{id}")
    public ModelAndView viewUserDetails(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();

        ClientDto clientDto = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(clientDto);
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(clientDto);
        modelAndView.addObject("client", clientDto);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/back")
    public ModelAndView goBack(){
        return showMeetingRequestList();
    }

    @RequestMapping("/admin/mailbox")
    public ModelAndView showAdminPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/admin/mail-box");

        return modelAndView;
    }

}
