package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.Invoice;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.service.Service;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private Service service;
    @Autowired
    private HttpSession session;
    private final MapperManager mapperManager = MapperManager.getInstance();

    public boolean isAuthenticated(){
        return (session.getAttribute("staff") != null);
    }

    @RequestMapping("/home")
    public ModelAndView showHomePage(String direction,
                                     String property,
                                     String successMsg,
                                     String errorMsg,
                                     @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        StaffDto staff = (StaffDto) session.getAttribute("staff");

        offset = (offset == null) ? 1 : offset;

        List<OffMeetingDto> meetingList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.APPROVED, PageRequest.of(offset - 1, 4, Sort.by(sortDirection, property)));
        }else {
            meetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.APPROVED, PageRequest.of(offset - 1, 4));
        }

        long lastOffset = service.getOffMeetingService().getLastOffset(staff, Status.APPROVED, 4);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staff, Status.APPROVED);

        modelAndView.addObject("meetingList", meetingList);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.addObject("totalMeetings", totalMeetings);
        if(successMsg != null){
            modelAndView.addObject("successMsg", successMsg);
        }
        if(errorMsg!= null){
            modelAndView.addObject("errorMsg", errorMsg);
        }

        modelAndView.setViewName("views/staff/profile");

        return modelAndView;
    }

    @RequestMapping("/home/sorted-by-{property}-{direction}")
    public ModelAndView showHomePageSortedNext(@RequestParam("offset") int offset,
                                               @PathVariable String property,
                                               @PathVariable String direction){
        return showHomePage(direction, property, null, null, offset);
    }

    @RequestMapping("/home/action={action}")
    public ModelAndView showHomePageAction(@PathVariable String action,
                                           @RequestParam("meetingId") String id,
                                           @RequestParam("property") String property,
                                           @RequestParam("direction") String direction,
                                           @Nullable @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(id);

        if(offMeeting == null){
            errorMsg = "An error occurred, cannot perform this action";
            return showHomePage(direction, property, successMsg, errorMsg, offset);
        }

        if(action.equalsIgnoreCase("fail")){
            offMeeting.setStatus(Status.FAILED);
            successMsg = "Deleted a failed meeting";
        }else if(action.equalsIgnoreCase("succeed")){
            offMeeting.setStatus(Status.SUCCESS);
            successMsg = "Updated meeting's status. Ready to create invoice";
        }else if(action.equalsIgnoreCase("cancel")){
            offMeeting.setStatus(Status.PENDING);
            offMeeting.setStaff(null);
            String msg = "Your meeting with " + ((StaffDto) session.getAttribute("staff")).getName() + " at " + offMeeting.getMeetingTime() + ", " + offMeeting.getMeetingDate() + " has been cancelled";
            service.sendNotification(offMeeting.getClient(), msg);
            successMsg = "Cancelled meeting with " + offMeeting.getClient().getName();
        }

        service.getOffMeetingService().save(offMeeting);

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showHomePage(direction, property, successMsg, errorMsg, offset);
    }


    @RequestMapping("/meeting-requests")
    public ModelAndView showMeetingRequestList(String direction,
                                               String property,
                                               String successMsg,
                                               String errorMsg,
                                               @Nullable @RequestParam("page") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        List<OffMeetingDto> offMeetingList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            offMeetingList = service.getOffMeetingService().getOffMeetingsSortedPerPage(PageRequest.of(offset - 1, 10, Sort.by(sortDirection, property)));
        }else {
            offMeetingList = service.getOffMeetingService().getOffMeetingsPerPage(PageRequest.of(offset - 1, 10));
        }

        long totalOffMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus();
        long lastOffset = service.getOffMeetingService().getLastOffset(10);

        modelAndView.addObject("offMeetingList", offMeetingList);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("property", property);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("totalOffMeetings", totalOffMeetings);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.setViewName("views/staff/meeting-req");
        if(successMsg != null){
            modelAndView.addObject("successMsg", successMsg);
        }
        if(errorMsg!= null){
            modelAndView.addObject("errorMsg", errorMsg);
        }

        return modelAndView;
    }

    @RequestMapping("/meeting-requests/sorted-by-{property}-{direction}")
    public ModelAndView showMeetingRequestsSortedPerPage1(@PathVariable String property,
                                                          @PathVariable String direction,
                                                          @Nullable @RequestParam("page") Integer offset){
        return showMeetingRequestList(direction, property, null, null, offset);
    }

    @RequestMapping("/meeting-requests/action={action}")
    public ModelAndView respondMeetingRequest(@PathVariable String action,
                                              @RequestParam("id") String meetingId,
                                              @RequestParam("property") String property,
                                              @RequestParam("direction") String direction,
                                              @RequestParam("offset") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting offMeeting = service.getOffMeetingService().findById(meetingId);

        if(offMeeting == null){
            errorMsg = "An error occurred, cannot perform this action";
        }else if(offMeeting.getStaff() != null){
            errorMsg = "This request has been reviewed by someone else";
        }else {
            offMeeting.setStaff(mapperManager.getStaffMapper().toEntity((StaffDto) session.getAttribute("staff")));
            offMeeting.setStatus(action.equalsIgnoreCase("decline") ? Status.DECLINED : Status.APPROVED);
            if(offMeeting.getStatus().equalsIgnoreCase(Status.DECLINED)){
                String msg = "Your meeting at " + offMeeting.getMeetingDate() + ", " + offMeeting.getMeetingTime() + " has been declined";
                service.sendNotification(offMeeting.getClient(), msg);
                successMsg = "Declined meeting with " + offMeeting.getClient().getName();
            }else {
                String msg =  "Your meeting at " + offMeeting.getMeetingDate() + ", " + offMeeting.getMeetingTime() + " has been approved";
                service.sendNotification(offMeeting.getClient(), msg);
                successMsg = "Approved meeting with " + offMeeting.getClient().getName();
            }
        }

        service.getOffMeetingService().save(offMeeting);

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showMeetingRequestList(direction, property, successMsg, errorMsg, offset);
    }

    @RequestMapping("/post-requests")
    public ModelAndView showPostRequestList(String direction,
                                            String property,
                                            String successMsg,
                                            String errorMsg,
                                            @Nullable @RequestParam("page") Integer offset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        List<PostDto> postRequestList;

        if(property != null && direction != null){
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            postRequestList = service.getPostService().getPostSortedPerPage(PageRequest.of(offset - 1, 10, Sort.by(sortDirection, property)));
        }else {
            postRequestList = service.getPostService().getPostsPerPage(PageRequest.of(offset - 1, 10));
        }

        long totalPostRequests = service.getPostService().getTotalPostRequests();
        long lastOffset = service.getPostService().getLastOffset(10);

        modelAndView.addObject("offset", offset);
        modelAndView.addObject("postRequestList", postRequestList);
        modelAndView.addObject("totalPostRequests", totalPostRequests);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("property", property);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.setViewName("views/staff/post-req");
        if(successMsg != null){
            modelAndView.addObject("successMsg", successMsg);
        }
        if(errorMsg!= null){
            modelAndView.addObject("errorMsg", errorMsg);
        }

        return modelAndView;
    }

    @RequestMapping("/post-requests/sorted-by-{property}-{direction}")
    public ModelAndView showPostRequestSortedPerPage(@PathVariable String direction,
                                                     @PathVariable String property,
                                                     @Nullable @RequestParam("page") Integer offset){
        return showPostRequestList(direction, property, null, null, offset);
    }

    @RequestMapping("/post-requests/action={action}")
    public ModelAndView respondPostRequest(@PathVariable String action,
                                           @RequestParam("id") String id,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("direction") String direction,
                                           @RequestParam("property") String property){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if(!isAuthenticated()){
            return modelAndView;
        }

        Post post = service.getPostService().findById(id);

        if(post == null){
            errorMsg = "An error occurred, cannot perform this action";
        }else if(!post.getStatus().equalsIgnoreCase(Status.PENDING)){
            errorMsg = "This post has already been handled by someone else";
        }else{
            if(action.equalsIgnoreCase("decline")){
                post.setStatus(Status.DECLINED);
                successMsg = "Declined post from " + post.getClient().getName();
                String msg = "Your post request of " + post.getCar().getName() + " in " + post.getPostDate() + ", " + post.getPostTime() + " has been declined";
                service.sendNotification(post.getClient(), msg);
            }else {
                post.setStatus(Status.APPROVED);
                successMsg = "Approved post from " + post.getClient().getName();
                String msg = "Your post request of " + post.getCar().getName() + " in " + post.getPostDate() + ", " + post.getPostTime() + " has been approved";
                service.sendNotification(post.getClient(), msg);
                service.configSearchList();
            }
            service.getPostService().save(post);
        }

        property = property.isEmpty() ? null : property;
        direction = direction.isEmpty() ? null : direction;

        return showPostRequestList(direction,property, successMsg, errorMsg, offset);
    }

    @RequestMapping("/user-details")
    public ModelAndView viewUserDetails(@RequestParam("id") String id,
                                        @Nullable @RequestParam("mOffset") Integer mOffset,
                                        @Nullable @RequestParam("pOffset") Integer pOffset){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        mOffset = (mOffset == null) ? 1 : mOffset;
        pOffset = (pOffset == null) ? 1 : pOffset;

        ClientDto client = service.getClientService().findById(id);
        List<OffMeetingDto> offMeetingsByClient = service.getOffMeetingService().getOffMeetingsByClient(client, PageRequest.of(mOffset - 1, 3));
        List<PostDto> postsByClient = service.getPostService().getPostsByClient(client, PageRequest.of(pOffset - 1, 3));
        long lastMOffset = service.getOffMeetingService().getLastOffset(client, 3);
        long lastPOffset = service.getPostService().getLastOffset(client, 3);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByClient(client);
        long totalPosts = service.getPostService().getTotalPostRequests(client);
        modelAndView.addObject("client", client);
        modelAndView.addObject("offMeetingList", offMeetingsByClient);
        modelAndView.addObject("postList", postsByClient);
        modelAndView.addObject("totalMeetings", totalMeetings);
        modelAndView.addObject("totalPosts", totalPosts);
        modelAndView.addObject("mOffset", mOffset);
        modelAndView.addObject("pOffset", pOffset);
        modelAndView.addObject("lastMOffset", lastMOffset);
        modelAndView.addObject("lastPOffset", lastPOffset);
        modelAndView.setViewName("views/staff/client-details");

        return modelAndView;
    }

    @RequestMapping("/create-invoice")
    public ModelAndView showCreateInvoicePage(@Nullable @RequestParam("offset") Integer offset,
                                              String successMsg,
                                              String errorMsg){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        offset = (offset == null) ? 1 : offset;

        StaffDto staff = (StaffDto) session.getAttribute("staff");
        List<OffMeetingDto> offMeetingList = service.getOffMeetingService().findOffMeetingsByStaffAndStatus(staff, Status.SUCCESS, PageRequest.of(offset - 1, 5));
        long lastOffset = service.getOffMeetingService().getLastOffset(staff, Status.SUCCESS, 5);
        long totalMeetings = service.getOffMeetingService().getTotalOffMeetingsByStaffAndStatus(staff, Status.SUCCESS);

        modelAndView.addObject("offMeetingList", offMeetingList);
        modelAndView.addObject("lastOffset", lastOffset);
        modelAndView.addObject("offset", offset);
        modelAndView.addObject("totalMeetings", totalMeetings);
        modelAndView.setViewName("views/staff/create-invoice");
        if(successMsg != null){
            modelAndView.addObject("successMsg", successMsg);
        }
        if(errorMsg!= null){
            modelAndView.addObject("errorMsg", errorMsg);
        }

        return modelAndView;
    }

    @RequestMapping("/create-invoice/{action}")
    public ModelAndView showInvoiceDetailsPage(@RequestParam("id") String id,
                                               @PathVariable String action){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if(!isAuthenticated()){
            return modelAndView;
        }

        OffMeetingDto offMeeting = mapperManager.getOffMeetingMapper().toDto(service.getOffMeetingService().findById(id));

        if(offMeeting == null){
            errorMsg = "An error occurred, cannot perform this action";
        }else {
            if(action.equalsIgnoreCase("create")){
                modelAndView.addObject("meeting", offMeeting);
                modelAndView.setViewName("views/staff/invoice-details");
                return modelAndView;
            }else if(action.equalsIgnoreCase("cancel")){
                offMeeting.setStatus(Status.APPROVED);
                service.getOffMeetingService().save(mapperManager.getOffMeetingMapper().toEntity(offMeeting));
                successMsg = "Meeting has been moved to your meetings queue";
            }
        }

        return showCreateInvoicePage(null, successMsg, errorMsg);
    }

    @RequestMapping("/create-invoice/confirm")
    public ModelAndView createInvoiceConfirm(@RequestParam("id") String id,
                                             @Nullable @RequestParam("notes") String notes,
                                             @RequestParam("tax") String tax){
        ModelAndView modelAndView = new ModelAndView("views/user/login");
        String errorMsg = null;
        String successMsg = null;

        if (!isAuthenticated()){
            return modelAndView;
        }

        OffMeeting meeting = service.getOffMeetingService().findById(id);

        if (meeting == null){
            errorMsg = "An error occurred, cannot perform this action";
        } else {
            Invoice invoice = Invoice.builder()
                    .client(meeting.getClient())
                    .staff(mapperManager.getStaffMapper().toEntity((StaffDto) session.getAttribute("staff")))
                    .car(meeting.getCar())
                    .createDate(LocalDate.now())
                    .createTime(LocalTime.now())
                    .status(Status.PAID)
                    .tax(tax)
                    .total(Util.calculateTotal(meeting.getCar().getPrice(), tax))
                    .otherInformation(notes)
                    .build();

            meeting.getCar().setStatus(Status.BOUGHT);
            meeting.setStatus(Status.DONE);
            meeting.getCar().getPost().setStatus(Status.COMPLETED);

            service.getInvoiceService().save(invoice);
            service.getOffMeetingService().save(meeting);
            successMsg = "Invoice has been created";
        }

        return showCreateInvoicePage(null, successMsg, errorMsg);
    }

    @RequestMapping("/car-details")
    public ModelAndView showCarDetails(@RequestParam("id") String id){
        ModelAndView modelAndView = new ModelAndView("views/user/login");

        if(!isAuthenticated()){
            return modelAndView;
        }

        CarDto car = service.getCarService().findCarById(id);
        modelAndView.addObject("car", car);
        modelAndView.setViewName("views/staff/car-details");

        return modelAndView;
    }

    @RequestMapping("/log-out")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();

        session.removeAttribute("staff");
        modelAndView.setViewName("views/user/index");

        return modelAndView;
    }


    // change password
    @RequestMapping("/bb")
    public boolean changeStaffPassword1(@RequestParam("id") String id,@RequestParam("oldPassword") String oldPass,@RequestParam("newPassword") String newPass){
        return service.getStaffService().changePassword(id,oldPass,newPass);
    }

}
