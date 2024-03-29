package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Email;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

@Controller
public class LogInController {
    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;

    @RequestMapping("/login-form")
    ModelAndView access(@RequestParam("email") String email, @RequestParam("password") String password){
       ModelAndView modelAndView = new ModelAndView();
        StaffDto staffDto = service.getStaffService().login(email, password);
        ClientDto clientDto = service.getClientService().login(email, password);
        AdminDto adminDto = service.getAdminService().login(email,password);
        if(staffDto != null){
            modelAndView.setViewName("views/staff/profile");
            session.setAttribute("staff", staffDto);

            List<OffMeetingDto> meetingList;

            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED, PageRequest.of(0, 4));
            long lastOffset = service.getOffMeetingService().getLastOffset(staffDto, Status.APPROVED, 4);
            long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staffDto, Status.APPROVED);
            modelAndView.addObject("meetingList", meetingList);
            modelAndView.addObject("property", null);
            modelAndView.addObject("direction", null);
            modelAndView.addObject("offset", 1);
            modelAndView.addObject("lastOffset", lastOffset);
            modelAndView.addObject("totalMeetings", totalMeetings);
        }

        else if(clientDto != null){

            modelAndView.setViewName("views/user/index");
            session.setAttribute("client", clientDto);
        }

        else if(adminDto != null){
            Map<String, Long> revenue = service.getPostService().getAnnualRevenue(2023);
            List<PostDto> listPost = new ArrayList<>();
            listPost = service.getPostService().getNewestPost();
            int numberOfUser = service.getClientService().getNumOfUser();
            modelAndView.addObject("keys", revenue.keySet())
                    .addObject("values", revenue.values())
                    .addObject("listPost",listPost)
                    .addObject("num",numberOfUser)
                    .setViewName("views/admin/index");
            session.setAttribute("admin", adminDto);
        }

        else {
            modelAndView.addObject("email",email);
            modelAndView.addObject("loginMess","Wrong email or password");
            modelAndView.addObject("status","fail");
            modelAndView.setViewName("views/user/login");
        }

        return modelAndView.addObject("postList", service.getPostService().findPriorPosts());

    }
    @RequestMapping(value = "/register-form", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("name") String name){
            ModelAndView modelAndView = new ModelAndView();


            if(service.getClientService().isExist(email) ||service.getStaffService().isExist(email)||service.getAdminService().isExist(email)){

                modelAndView.addObject("loginMess","This email is already registered. Please register with another email");
                modelAndView.addObject("status","fail");
                modelAndView.setViewName("views/user/login");

            }else {
                modelAndView.addObject("loginMess","We've sent you an email. Please check your email to proceed.");
                modelAndView.addObject("status","success");
                modelAndView.setViewName("views/user/login");

                Email registrationEmail = new Email();
                Map<String, Object> emailProperties = new HashMap<>();

                registrationEmail.setTo(email);
                registrationEmail.setFrom("timelessride3@gmail.com");
                registrationEmail.setSubject("Register Your Account");
                registrationEmail.setTemplate("views/email/email-registration.html");

                password = Util.encodePassword(password);
                String url = String.format("http://localhost:8080/register-confirm?name=%s&email=%s&password=%s", name, email, password);

                emailProperties.put("registrationUrl", url);
//                alertEmailProperties.put("expireDate", p.getExpireDate());
//                alertEmailProperties.put("plan", p.getPlan());

                registrationEmail.setProperties(emailProperties);

                new Thread(() -> {
                    try {
                        service.getEmailService().sendHTMLMessage(registrationEmail);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }).start();


            }
            return modelAndView;
    }

    @RequestMapping("/register-confirm")
    public ModelAndView confirmRegistration(@RequestParam("email") String email, @RequestParam("password") String password,@RequestParam("name") String name){
        ModelAndView modelAndView = new ModelAndView();

        if(service.getClientService().isExist(email) ||service.getStaffService().isExist(email)||service.getAdminService().isExist(email)){

            modelAndView.addObject("loginMess","This email is already registered. Please register with another email");
            modelAndView.addObject("status","fail");
            modelAndView.setViewName("views/user/login");
        }else {
            Client client = Client.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .joinDate(LocalDate.now())
                    .build();

            service.getClientService().save(client);

            modelAndView.setViewName("views/user/index");
            session.setAttribute("client", service.getClientService().findByEmail(client.getEmail()));
        }

        return modelAndView;
    }

}
