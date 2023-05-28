package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OffMeetingRepository extends CrudRepository<OffMeeting, Integer> {
    List<OffMeeting> findOffMeetingsByClient(Client client);
}