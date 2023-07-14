package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Feedback;
import com.duy.carshowroomdemo.entity.OffMeeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
<<<<<<< HEAD

    @Query("select f from Feedback f where f.rating = :star-0.5 or f.rating = :star order by f.createdAt")
    Page<Feedback> findAllByRating(@Param("star") Double star, Pageable pageable);
=======
    @Query("select avg(f.rating) from Feedback  f")
    double getAvgStar();
    @Query("select count(f) from Feedback  f")
    int getTotalFb();
>>>>>>> 7f499e8aa629d3fd6713aea288c5cd0709fefb76
}