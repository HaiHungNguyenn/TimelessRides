package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    HttpSession session;

    public void configSearchList(){
        List<CarDto> carList = getCarService().getCarList();
//      make
        List<String> makes = new ArrayList<>();
        carList.forEach(x -> {makes.add(x.getCarDescription().getMake());});
        HashSet<String> makeList = new HashSet<>(makes);
        makes.clear();
        makes.addAll(makeList);
//        model
        List<String> models = new ArrayList<>();
        carList.forEach(x -> {models.add(x.getCarDescription().getModel());});
        HashSet<String> modelList = new HashSet<>(models);
        models.clear();
        models.addAll(modelList);
//        body
        List<String> bodys = new ArrayList<>();
        carList.forEach(x -> {bodys.add(x.getCarDescription().getBody());});
        HashSet<String> bodyList = new HashSet<>(bodys);
        bodys.clear();
        bodys.addAll(bodyList);
//        tranmission
        List<String> trans = new ArrayList<>();
        carList.forEach(x -> {trans.add(x.getCarDescription().getTransmission());});
        HashSet<String> transList = new HashSet<>(trans);
        trans.clear();
        trans.addAll(transList);
//        fuel style
        List<String> fuels = new ArrayList<>();
        carList.forEach(x -> {fuels.add(x.getCarDescription().getFuelType());});
        HashSet<String> fuelList = new HashSet<>(fuels);
        fuels.clear();
        fuels.addAll(fuelList);


        session.setAttribute("makeList",makes);
        session.setAttribute("modelList",models);
        session.setAttribute("bodyList",bodys);
        session.setAttribute("tranmissionList",trans);
        session.setAttribute("fuelList",fuels);

    }
}
