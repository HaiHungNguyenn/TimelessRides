package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.OffMeetingRepository;
import com.duy.carshowroomdemo.util.Status;
import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OffMeetingService {
    @Autowired
    private OffMeetingRepository offMeetingRepository;

    private final MapperManager mapperManager = MapperManager.getInstance();

    public int countAllRequests(){
        return (int) offMeetingRepository.count();
    }

    public List<OffMeetingDto> getAllMeetingRequests(){
        List<OffMeetingDto> offMeetingList = new ArrayList<>();
        offMeetingRepository.findAll().forEach(x -> {
            offMeetingList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingList;
    }

    public List<OffMeetingDto> getOffMeetingsByClient(ClientDto clientDto, Pageable pageable){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findOffMeetingsByClient(mapperManager.getClientMapper().toEntity(clientDto),pageable).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsPerPage(Pageable pageable){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findAllByStatus(Status.PENDING, pageable)
                .forEach((x) -> offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x)));

        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsSortedPerPage(Pageable pageable){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findAllByStatus(Status.PENDING, pageable).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }

    public long getTotalOffMeetingsByStaffAndStatus() {
        return offMeetingRepository.countByStatus(Status.PENDING);
    }

    public long getLastOffset(int size) {
        return offMeetingRepository.findAllByStatus(Status.PENDING, PageRequest.of(0,size)).getTotalPages();
    }

    public long getLastOffset(ClientDto clientDto, int size) {
        return offMeetingRepository.findOffMeetingsByClient(mapperManager.getClientMapper().toEntity(clientDto), PageRequest.of(0, size)).getTotalPages();
    }

    public OffMeeting findById(String id) {
        return offMeetingRepository.findById(id).orElse(null);
    }

    public void save(OffMeeting offMeeting) {
        offMeetingRepository.save(offMeeting);
    }

    public List<OffMeetingDto> findOffMeetingsByStaffAndStatus(StaffDto staff, String status, Pageable pageable) {
        List<OffMeetingDto> offMeetingList = new ArrayList<>();
        offMeetingRepository.findAllByStaffAndStatus(mapperManager.getStaffMapper().toEntity(staff), status, pageable)
                .forEach((x) -> offMeetingList.add(mapperManager.getOffMeetingMapper().toDto(x)));
        return offMeetingList;
    }

    public long getLastOffset(StaffDto staff, String status, int size) {
        return offMeetingRepository.findAllByStaffAndStatus(mapperManager.getStaffMapper().toEntity(staff), status, PageRequest.of(0,size)).getTotalPages();
    }

    public long getTotalOffMeetingsByStaffAndStatus(StaffDto staff, String status) {
        return offMeetingRepository.countByStaffAndStatus(mapperManager.getStaffMapper().toEntity(staff), status);
    }

    public long getTotalOffMeetingsByClient(ClientDto client) {
        return offMeetingRepository.countByClient(mapperManager.getClientMapper().toEntity(client));
    }

    public List<OffMeetingDto> getMeetingsByDate(LocalDate date){
        List<OffMeetingDto> list = new ArrayList<>();
        offMeetingRepository.findOffMeetingsByWeek(getMonday(date), getSunday(date)).forEach(x ->
                list.add(mapperManager.getOffMeetingMapper().toDto(x)));
        return list;
    }

    public List<OffMeetingDto> getMeetingsByDateAndSlot(LocalDate date, LocalTime slot){
        List<OffMeetingDto> list = new ArrayList<>();
        slot.format(DateTimeFormatter.ISO_TIME);
        offMeetingRepository.findOffMeetingsByMeetingDateAndSlot(date, slot).forEach(x ->
                list.add(mapperManager.getOffMeetingMapper().toDto(x)));
        return list;
    }

    public LocalDate getMonday(LocalDate date){
        LocalDate monday = date;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    public LocalDate getSunday(LocalDate date){
        LocalDate sunday = date;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    public void delete(OffMeeting offMeeting) {
        offMeetingRepository.delete(offMeeting);
    }

    public List<OffMeetingDto> findByStaffId(String staffID,Pageable pageable) {
        List<OffMeetingDto> list = new ArrayList<>();
        offMeetingRepository.findByStaffId(staffID ,pageable).forEach(x -> list.add(mapperManager.getOffMeetingMapper().toDto(x)));
        return list;
    }

    public boolean isOffMeetingsByStaffLastOffset(String staffID, Pageable pageable) {
        return offMeetingRepository.findByStaffId(staffID ,pageable).isLast();
    }
}
