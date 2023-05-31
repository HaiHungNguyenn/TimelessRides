package com.duy.carshowroomdemo.controller;

//import com.duy.carshowroomdemo.services.Service;
import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Stack;

@Controller
public class StaffController {

    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    private final Stack<String> stack = new Stack<>();

    public boolean isAuthenticated(){
        return (session.getAttribute("staff") != null);
    }

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

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        modelAndView.setViewName("views/staff/profile");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests")
    public ModelAndView showMeetingRequestList(){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<OffMeetingDto> offMeetingList = service.getOffMeetingService().getOffMeetingsPerPage(0, 10);
        modelAndView.addObject("offMeetingList", offMeetingList);
        modelAndView.addObject("offset", 1);
        modelAndView.setViewName("views/staff/meeting-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests/page={offset}")
    public ModelAndView showMeetingRequestsPerPage(@PathVariable int offset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<OffMeetingDto> allOffMeetings = service.getOffMeetingService().getOffMeetingsPerPage(offset-1, 10);
        modelAndView.addObject("offMeetingList", allOffMeetings);
        modelAndView.addObject("offset", offset);
        modelAndView.setViewName("views/staff/meeting-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests/sorted-by-{property}-{direction}")
    public ModelAndView showMeetingRequestsSortedPerPage1(@PathVariable String property,
                                                          @PathVariable String direction){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<OffMeetingDto> allOffMeetings = service.getOffMeetingService().getOffMeetingsSortedPerPage(0, 10, property, direction);
        modelAndView.addObject("offMeetingList", allOffMeetings);
        modelAndView.addObject("offset", 1);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.setViewName("views/staff/meeting-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/meeting-requests/sorted-by-{property}-{direction}/page={offset}")
    public ModelAndView showMeetingRequestsSortedPerPage(@PathVariable String property,
                                                         @PathVariable String direction,
                                                         @PathVariable int offset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<OffMeetingDto> offMeetings = service.getOffMeetingService().getOffMeetingsSortedPerPage(offset-1, 10, property, direction);
        modelAndView.addObject("offMeetingList", offMeetings);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.setViewName("views/staff/meeting-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/post-requests")
    public ModelAndView showPostRequestList(){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<PostDto> allPostRequests = service.getPostService().getPostsPerPage(1, 10);
        modelAndView.addObject("offset", 1);
        modelAndView.addObject("postRequestList", allPostRequests);
        modelAndView.setViewName("views/staff/post-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/post-requests/page={offset}")
    public ModelAndView showPostRequestsPerPage(@PathVariable int offset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<PostDto> allPostRequests = service.getPostService().getPostsPerPage(offset, 10);
        modelAndView.addObject("postRequestList", allPostRequests);
        modelAndView.addObject("offset", offset);
        modelAndView.setViewName("views/staff/post-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/post-requests/sorted-by-{property}-{direction}")
    public ModelAndView showPostRequestSortedPerPage(@PathVariable String direction,
                                                     @PathVariable String property){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<PostDto> allPostRequests = service.getPostService().getPostSortedPerPage(0, 10, property, direction);
        modelAndView.addObject("postRequestList", allPostRequests);
        modelAndView.addObject("offset", 1);
        modelAndView.addObject("direction", direction);
        modelAndView.setViewName("views/staff/post-req");

        stack.push(request.getRequestURI());
        
        return modelAndView;
    }

    @RequestMapping("/staff/post-requests/sorted-by-{property}-{direction}/page={offset}")
    public ModelAndView showPostRequestSortedPerPage1(@PathVariable String direction,
                                                     @PathVariable String property,
                                                      @PathVariable int offset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        List<PostDto> allPostRequests = service.getPostService().getPostSortedPerPage(offset-1, 10, property, direction);
        modelAndView.addObject("postRequestList", allPostRequests);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("direction", direction);
        modelAndView.setViewName("views/staff/post-req");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/create-invoice")
    public ModelAndView showCreateInvoicePage(){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        modelAndView.setViewName("views/staff/create-invoice");

        stack.push(request.getRequestURI());

        return modelAndView;
    }

    @RequestMapping("/staff/user-details/{id}")
    public ModelAndView viewUserDetails(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        ClientDto clientDto = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(clientDto, 0, 3);
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(clientDto, 0, 3);
        modelAndView.addObject("client", clientDto);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.addObject("mOffset", 1);
        modelAndView.addObject("pOffset", 1);
        modelAndView.addObject("callBackUrl", stack.peek());
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/staff/user-details/{id}/meeting-requests-page={mOffset}")
    public ModelAndView viewUserDetailsPagedByMeetingRequest(@PathVariable String id,
                                                             @PathVariable int mOffset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        ClientDto clientDto = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(clientDto, mOffset-1, 3);
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(clientDto, 0, 3);
        modelAndView.addObject("client", clientDto);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.addObject("mOffset", mOffset);
        modelAndView.addObject("pOffset", 1);
        modelAndView.addObject("callBackUrl", stack.peek());
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/staff/user-details/{id}/post-requests-page={pOffset}")
    public ModelAndView viewUserDetailsPagedByPostRequest(@PathVariable String id,
                                                          @PathVariable int pOffset){
        ModelAndView modelAndView = new ModelAndView();

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        ClientDto clientDto = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(clientDto, 0, 3);
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(clientDto, pOffset-1, 3);
        modelAndView.addObject("client", clientDto);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.addObject("mOffset", 1);
        modelAndView.addObject("pOffset", pOffset);
        modelAndView.addObject("callBackUrl", stack.peek());
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/staff/create-invoice/create")
    public ModelAndView createInvoice(@RequestParam("fullName") String fullName,
                                      @RequestParam("email") String email,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("carName") String carName,
                                      @RequestParam("brand") String brand,
                                      @RequestParam("boughtYear") int boughtYear,
                                      @RequestParam("licensePlate") String licensePlate,
                                      @RequestParam("additionalInfo") String additionalInfo){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/staff/create-invoice");

        if(!isAuthenticated()){
            modelAndView.setViewName("views/user/login");
            return modelAndView;
        }

        ClientDto client = service.getClientService().getClientByEmailAndNameAndPhone(email, fullName, phone);

        if(client == null){
            modelAndView.addObject("errorMsg", "Client not found");
            modelAndView.addObject("fullName", fullName);
            modelAndView.addObject("email", email);
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("carName", carName);
            modelAndView.addObject("brand", brand);
            modelAndView.addObject("boughtYear", boughtYear);
            modelAndView.addObject("licensePlate", licensePlate);
            modelAndView.addObject("additionalInfo", additionalInfo);
            return modelAndView;
        }

        CarDto car = service.getCarService().findCarByNameAndBrandAndBoughtYearAndLicensePlate(carName, brand, boughtYear, licensePlate);

        if(car == null){
            modelAndView.addObject("errorMsg", "Car not found");
            modelAndView.addObject("fullName", fullName);
            modelAndView.addObject("email", email);
            modelAndView.addObject("phone", phone);
            modelAndView.addObject("carName", carName);
            modelAndView.addObject("brand", brand);
            modelAndView.addObject("boughtYear", boughtYear);
            modelAndView.addObject("licensePlate", licensePlate);
            modelAndView.addObject("additionalInfo", additionalInfo);
            return modelAndView;
        }

        boolean isSavedInvoice = service.getInvoiceService().createNewInvoice(client, (StaffDto) session.getAttribute("staff"), car, additionalInfo);
        if(isSavedInvoice){
            modelAndView.addObject("successMsg", "Invoice created successfully");
        }else {
            modelAndView.addObject("errorMsg", "An error occurred");
        }

        stack.push(request.getRequestURI());

        return modelAndView;
    }


    @RequestMapping("/staff/log-out")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();

        session.removeAttribute("staff");
        modelAndView.setViewName("views/user/index");

        return modelAndView;
    }



    @RequestMapping("/admin/mailbox")
    public ModelAndView showAdminPage(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("views/admin/mail-box");

        return modelAndView;
    }

}
