package com.duy.carshowroomdemo.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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
    private ClientService clientService = new ClientService();
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
    private ImageService imageService;
}
