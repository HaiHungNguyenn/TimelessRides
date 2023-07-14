package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    @Query("select avg(f.rating) from Feedback  f")
    double getAvgStar();
    @Query("select count(f) from Feedback  f")
    int getTotalFb();
}