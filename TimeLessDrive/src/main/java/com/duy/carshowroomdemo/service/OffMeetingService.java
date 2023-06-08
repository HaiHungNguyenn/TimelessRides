package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.dto.StaffDto;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Staff;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.OffMeetingRepository;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffMeetingService {
    @Autowired
    private OffMeetingRepository offMeetingRepository;

    private final MapperManager mapperManager = new MapperManager();


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
}
