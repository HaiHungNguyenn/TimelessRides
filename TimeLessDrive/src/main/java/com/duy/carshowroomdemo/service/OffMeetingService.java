package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.OffMeetingRepository;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffMeetingService {
    @Autowired
    private OffMeetingRepository offMeetingRepository;

    private MapperManager mapperManager = new MapperManager();


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

    public List<OffMeetingDto> getOffMeetingsByClient(ClientDto clientDto, int offset, int size){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findOffMeetingsByClient(mapperManager.getClientMapper().toEntity(clientDto),PageRequest.of(offset, size)).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsPerPage(int offset, int size){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findAllByStatus(Status.PENDING, PageRequest.of(offset, size))
                .forEach((x) -> offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x)));

//        offMeetingRepository.findAll(PageRequest.of(offset, size)).forEach((x) -> {
//            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
//        });

        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsSortedPerPage(int offset, int size, String property, String direction){
        Sort.Direction sortDirection = (direction.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findAllByStatus(Status.PENDING, PageRequest.of(offset,size, Sort.by(sortDirection, property))).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }

    public long getTotalOffMeetings() {
        return offMeetingRepository.countByStatus(Status.PENDING);
    }

    public long getLastOffset(int size) {
        return offMeetingRepository.findAllByStatus(Status.PENDING, PageRequest.of(0,size)).getTotalPages();
    }

    public long getLastOffset(ClientDto clientDto, int size) {
        int totalElements =  offMeetingRepository.findOffMeetingsByClient(mapperManager.getClientMapper().toEntity(clientDto),PageRequest.of(0, size)).size();
        return (long) Math.ceil((double) totalElements / size);
    }

    public OffMeeting findById(String id) {
        return offMeetingRepository.findById(id).orElse(null);
    }

    public void save(OffMeeting offMeeting) {
        offMeetingRepository.save(offMeeting);
    }
}
