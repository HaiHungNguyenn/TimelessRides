package com.duy.carshowroomdemo.mapper;

import lombok.Getter;

@Getter
public class MapperManager {
    private static MapperManager instance;
    public static MapperManager getInstance(){
        if(instance == null){
            instance = new MapperManager();
        }
        return instance;
    }
    private MapperManager(){}
    private final CarImageMapper            carImageMapper              = new CarImageMapper();
    private final CarDescriptionMapper      carDescriptionMapper        = new CarDescriptionMapper();
    private final CarMapper                 carMapper                   = new CarMapper();
    private final AdminMapper               adminMapper                 = new AdminMapper();
    private final ClientMapper              clientMapper                = new ClientMapper();
    private final FeedbackMapper            feedbackMapper              = new FeedbackMapper();
    private final InvoiceMapper             invoiceMapper               = new InvoiceMapper();
    private final OffMeetingMapper          offMeetingMapper            = new OffMeetingMapper();
    private final PostMapper                postMapper                  = new PostMapper();
    private final ShowroomMapper            showroomMapper              = new ShowroomMapper();
    private final StaffMapper               staffMapper                 = new StaffMapper();
    private final ClientNotificationMapper  clientNotificationMapper    = new ClientNotificationMapper();
}
