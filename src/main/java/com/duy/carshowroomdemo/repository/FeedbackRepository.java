package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
}