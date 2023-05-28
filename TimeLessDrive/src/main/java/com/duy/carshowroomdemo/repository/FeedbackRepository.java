package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
}