package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.FeedbackDto;
import com.duy.carshowroomdemo.entity.Feedback;
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

    public List<Feedback> findFeedbacksPerPage(Pageable pageable) {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackRepository.findAll(pageable).forEach(x -> feedbackList.add(x));
        return feedbackList;
    }

    public boolean isLastPageOfFeedbacksPerPage(Pageable pageable) {
        return feedbackRepository.findAll(pageable).isLast();
    }

    public List<Feedback> findFeedbacksByRating(Double star,Pageable pageable) {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackRepository.findAllByRating(star ,pageable).forEach(x -> feedbackList.add(x));
        return feedbackList;
    }

    public boolean isLastPageOfFeedbacksByRating(Double star, Pageable pageable) {
        return feedbackRepository.findAllByRating(star ,pageable).isLast();
    }

    public List<Feedback> findAllFeedBacks() {
        List<Feedback> feedbackList = new ArrayList<>();
        feedbackRepository.findAll().forEach(x -> feedbackList.add(x));
        return feedbackList;
    }

    public double getAverageRating() {
        double average=0;
        List<Feedback> feedbackList = findAllFeedBacks();
        for(Feedback f : feedbackList){
            average += f.getRating();
        }
        average = average/feedbackList.size();
        return average;
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public double getAvg() {
        return feedbackRepository.getAvgStar();
    }

    public int getNumOfFeedback() {
        return feedbackRepository.getTotalFb();
    }
}
