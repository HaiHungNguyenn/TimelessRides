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
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OffMeetingRepository extends JpaRepository<OffMeeting, String> {

    long countByStaffAndStatus(Staff staff, String status);

    Page<OffMeeting> findOffMeetingsByClient(Client client, Pageable pageable);

    Page<OffMeeting> findAllByStatus(String status, Pageable pageable);

    long countByStatus(String status);

    Page<OffMeeting> findAllByStaffAndStatus(Staff staff, String status, Pageable pageable);

    long countByClient(Client client);

    Page<OffMeeting> findByStaffId(String staffID,Pageable pageable);
    @Query("select m from OffMeeting m where m.meetingDate = :date and m.meetingTime = cast(:slot as localtime) order by m.meetingTime")
    List<OffMeeting> findOffMeetingsByMeetingDateAndSlot(@Param("date") LocalDate date, @Param("slot") LocalTime slot);

    @Query("select m from OffMeeting m where m.meetingDate >= :start and m.meetingDate <= :end order by m.meetingTime, m.meetingDate")
    List<OffMeeting> findOffMeetingsByWeek(@Param("start") LocalDate start, @Param("end") LocalDate end);
    //    List<OffMeeting> findAll(Pageable pageable, Sort sort);

    @Query("select m from OffMeeting m where m.car.id = :id and m.status = 'Pending' or m.status = 'Approved' order by m.meetingTime, m.meetingDate")
    List<OffMeeting> findOccupiedOffMeetingsByCarId(@Param("id") String id);
}