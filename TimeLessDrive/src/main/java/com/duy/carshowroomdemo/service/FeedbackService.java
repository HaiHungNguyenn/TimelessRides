package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.FeedbackDto;
import com.duy.carshowroomdemo.entity.Feedback;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    MapperManager mapperManager = MapperManager.getInstance();

    public List<Feedback> findFeedbacksPerPage(Pageable pageable) {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackRepository.findAll(pageable).forEach(x -> feedbackList.add(x));
        return feedbackList;
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
