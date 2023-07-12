package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.*;
import com.duy.carshowroomdemo.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MapperManager {
    private static MapperManager instance;
    public static MapperManager getInstance(){
        if (instance == null){
            instance = new MapperManager();
        }
        return instance;
    }
    private MapperManager(){}
    private final ModelMapper<CarImage, CarImageDto>                        carImageMapper              = new ModelMapper<>();
    private final ModelMapper<CarDescription, CarDescriptionDto>            carDescriptionMapper        = new ModelMapper<>();
    private final ModelMapper<Car, CarDto>                                  carMapper                   = new ModelMapper<>();
    private final ModelMapper<Admin, AdminDto>                              adminMapper                 = new ModelMapper<>();
    private final ModelMapper<Client, ClientDto>                            clientMapper                = new ModelMapper<>();
    private final ModelMapper<Feedback, FeedbackDto>                        feedbackMapper              = new ModelMapper<>();
    private final ModelMapper<Invoice, InvoiceDto>                          invoiceMapper               = new ModelMapper<>();
    private final ModelMapper<OffMeeting, OffMeetingDto>                    offMeetingMapper            = new ModelMapper<>();
    private final ModelMapper<Post, PostDto>                                postMapper                  = new ModelMapper<>();
    private final ModelMapper<Showroom, ShowroomDto>                        showroomMapper              = new ModelMapper<>();
    private final ModelMapper<Staff, StaffDto>                              staffMapper                 = new ModelMapper<>();
    private final ModelMapper<ClientNotification, ClientNotificationDto>    clientNotificationMapper    = new ModelMapper<>();
}
