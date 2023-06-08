package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface OffMeetingRepository extends JpaRepository<OffMeeting, String> {

    long countByStaffAndStatus(Staff staff, String status);

    Page<OffMeeting> findOffMeetingsByClient(Client client, Pageable pageable);

    Page<OffMeeting> findAllByStatus(String status, Pageable pageable);

    long countByStatus(String status);

    Page<OffMeeting> findAllByStaffAndStatus(Staff staff, String status, Pageable pageable);

    long countByClient(Client client);
    //    List<OffMeeting> findAll(Pageable pageable, Sort sort);
}