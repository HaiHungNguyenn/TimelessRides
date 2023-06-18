package com.duy.carshowroomdemo.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapperManager {
    private static MapperManager instance;
    public static MapperManager getInstance(){
        if(instance == null){
            instance = new MapperManager();
        }
        return instance;
    }
    private MapperManager(){}
    private CarImageMapper carImageMapper = new CarImageMapper();
    private CarDescriptionMapper carDescriptionMapper = new CarDescriptionMapper();
    private CarMapper carMapper = new CarMapper();
    private AdminMapper adminMapper = new AdminMapper();
    private ClientMapper clientMapper = new ClientMapper();
    private FeedbackMapper feedbackMapper = new FeedbackMapper();
    private InvoiceMapper invoiceMapper = new InvoiceMapper();
    private OffMeetingMapper offMeetingMapper = new OffMeetingMapper();
    private PostMapper postMapper = new PostMapper();
    private ShowroomMapper showroomMapper = new ShowroomMapper();
    private StaffMapper staffMapper = new StaffMapper();
}
