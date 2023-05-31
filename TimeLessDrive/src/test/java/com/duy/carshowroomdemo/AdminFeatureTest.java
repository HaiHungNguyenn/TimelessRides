package com.duy.carshowroomdemo;


import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.util.Util;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.duy.carshowroomdemo.repository.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AdminFeatureTest {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CarDescriptionRepository carDescriptionRepository;
    @Autowired
    private CarImageRepository carImageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private OffMeetingRepository offMeetingRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ShowroomRepository showroomRepository;
    @Autowired
    private StaffRepository staffRepository;

    MapperManager mapperManager = new MapperManager();
    // dele
    @Test
    public void testDeleteClientByID(){
        try {
            clientRepository.deleteById("008355ea-2e36-41f1-b5eb-eae7e274659d");
            System.out.println(true);
        } catch (Exception e) {
            // Handle the exception (e.g., log it)
            System.out.println(false);

        }
    }
    // edit client info
    @Test
    public void testEditClientPW(){
        String oldPasword = "password123";
        String newPassword ="12345";
        String id = "008355ea-2e36-41f1-b5eb-eae7e274659d";
        String msg ="";
        Client client = clientRepository.findById(id).get();
        System.out.println(client);
        System.out.println(Util.isValidPW(oldPasword,client.getPassword()));
        try {
            if(Util.isValidPW(oldPasword,client.getPassword())){
                client.setPassword(Util.encodePassword(newPassword));
                clientRepository.save(client);
                msg = "change password sucessfully";
            }else{msg = "fail to change password";}


        }catch (Exception e){
            System.out.println(e);

        }
        System.out.println(msg);

    }




}
