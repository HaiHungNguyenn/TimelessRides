package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OffMeetingRepository extends JpaRepository<OffMeeting, String> {
    List<OffMeeting> findOffMeetingsByClient(Client client, Pageable pageable);
//    List<OffMeeting> findAll(Pageable pageable, Sort sort);
}