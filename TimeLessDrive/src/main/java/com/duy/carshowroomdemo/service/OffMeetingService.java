package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.OffMeetingRepository;
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

    public List<OffMeetingDto> getOffMeetingsByClient(ClientDto clientDto){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findOffMeetingsByClient(mapperManager.getClientMapper().toEntity(clientDto)).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsPerPage(int offset, int size){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();

        offMeetingRepository.findAll(PageRequest.of(offset, size)).forEach((x) -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });

        return offMeetingDtoList;
    }

    public List<OffMeetingDto> getOffMeetingsSortedPerPage(int offset, int size, String property){
        List<OffMeetingDto> offMeetingDtoList = new ArrayList<>();
        offMeetingRepository.findAll(PageRequest.of(offset,size, Sort.by(property))).forEach(x -> {
            offMeetingDtoList.add(mapperManager.getOffMeetingMapper().toDto(x));
        });
        return offMeetingDtoList;
    }
}
