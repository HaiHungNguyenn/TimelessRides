package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.FeedbackDto;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    MapperManager mapperManager = MapperManager.getInstance();

    public List<FeedbackDto> findFeedbacksPerPage(Pageable pageable) {
        List<FeedbackDto> feedbackList = new ArrayList<>();
        feedbackRepository.findAll(pageable).forEach(x -> feedbackList.add(mapperManager.getFeedbackMapper().toDto(x)));
        return feedbackList;
    }
}
