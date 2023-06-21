package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.ClientNotification;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.entity.StaffNotification;
import com.duy.carshowroomdemo.util.Status;
import com.duy.carshowroomdemo.util.Util;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private StaffService staffService;
    @Autowired
    private CarService carService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CarDescriptionService carDescriptionService;
    @Autowired
    private CarImageService carImageService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private OffMeetingService offMeetingService;
    @Autowired
    private PostService postService;
    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StaffNotificationService staffNotificationService;
    @Autowired
    private ClientNotificationService clientNotificationService;
    public <T> void sendNotification(T receiver, String content){

        if (receiver.getClass().equals(Client.class)) {
            ClientNotification notification = ClientNotification.builder()
                    .createDate(LocalDate.now())
                    .createTime(LocalTime.now())
                    .receiver((Client) receiver)
                    .content(content)
                    .status(Status.SENT)
                    .build();
            clientNotificationService.save(notification);
        }else if (receiver.getClass().equals(Staff.class)){
            StaffNotification notification = StaffNotification.builder()
                    .createDate(LocalDate.now())
                    .createTime(LocalTime.now())
                    .receiver((Staff) receiver)
                    .content(content)
                    .status(Status.SENT)
                    .build();
            staffNotificationService.save(notification);
        }
    }

    @Autowired
    HttpSession session;

    public void configSearchList(){
//        List<CarDto> carList = getCarService().getCarList();
//      make
//        List<String> makes = new ArrayList<>();
//        carList.forEach(x -> {makes.add(x.getCarDescription().getMake());});
//        HashSet<String> makeList = new HashSet<>(makes);
//        makes.clear();
//        makes.addAll(makeList);

//        model
//        List<String> models = new ArrayList<>();
//        carList.forEach(x -> {models.add(x.getCarDescription().getModel());});
//        HashSet<String> modelList = new HashSet<>(models);
//        models.clear();
//        models.addAll(modelList);

//        body
//        List<String> bodys = new ArrayList<>();
//        carList.forEach(x -> {bodys.add(x.getCarDescription().getBody());});
//        HashSet<String> bodyList = new HashSet<>(bodys);
//        bodys.clear();
//        bodys.addAll(bodyList);

//        tranmission
//        List<String> trans = new ArrayList<>();
//        carList.forEach(x -> {trans.add(x.getCarDescription().getTransmission());});
//        HashSet<String> transList = new HashSet<>(trans);
//        trans.clear();
//        trans.addAll(transList);

//        fuel style
//        List<String> fuels = new ArrayList<>();
//        carList.forEach(x -> {fuels.add(x.getCarDescription().getFuelType());});
//        HashSet<String> fuelList = new HashSet<>(fuels);
//        fuels.clear();
//        fuels.addAll(fuelList);

        List<String> makes = carDescriptionService.getMakeList();
        List<String> models = carDescriptionService.getModelList();
        List<String> bodies = carDescriptionService.getBodyList();
        List<String> trans = carDescriptionService.getTransList();
        List<String> fuels = carDescriptionService.getFuelList();

        List<String> postMakes = Util.getMakeList();
        postMakes.addAll(makes);
        postMakes = new HashSet<>(postMakes).stream().toList();

        List<String> postModels = Util.getModelList();
        postModels.addAll(models);
        postModels = new HashSet<>(postModels).stream().toList();

        List<String> bodyTypes = Util.getBodyTypes();
        bodyTypes.addAll(bodies);
        bodyTypes = new HashSet<>(bodyTypes).stream().toList();

        session.setAttribute("makeList",makes);
        session.setAttribute("modelList",models);
        session.setAttribute("bodyList",bodies);
        session.setAttribute("tranmissionList",trans);
        session.setAttribute("fuelList",fuels);
        session.setAttribute("postMakes",postMakes);
        session.setAttribute("postModels",postModels);
        session.setAttribute("postBodies",bodyTypes);


    }
}
