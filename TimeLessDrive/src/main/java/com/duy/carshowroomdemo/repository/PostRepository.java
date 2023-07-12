package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String>{

    LocalDate CURRENT_DATE = LocalDate.now();

    Month month = CURRENT_DATE.getMonth();

    int year = CURRENT_DATE.getYear();

    Page<Post> findPostsByClient(Client client, Pageable pageable);

    @Query("select p from Post p where p.client.id = :id order by p.postDate")
    Page<Post> findPostsByClientId(@Param("id") String clientId, Pageable pageable);

    List<Post> findAllByStatusIs(String status);
    @Query("select p from Post p where p.status='Approved' AND p.car.carDescription.make = :value AND p.expireDate > :date order by p.postDate")
    Page<Post> findAllByStatusIsAndCarMake(@Param("value") String value, Pageable pageable ,LocalDate date );
    @Query("select p from Post p where p.status='Approved' and p.car.carDescription.make = :value and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarMakeWithPriceASC(String value, Pageable pageable,LocalDate date );
    @Query("select p from Post p where p.status='Approved' and :date < p.expireDate")
    List<Post> test(LocalDate date);


    @Query("select p from Post p where p.status='Approved' and p.car.carDescription.make = :value and p.expireDate > :date order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarMakeWithPriceDESC(String value, Pageable pageable,LocalDate date );


    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value and p.expireDate > :date order by p.postDate")
    Page<Post> findAllByStatusIsAndCarModel(@Param("value") String value, Pageable pageable,LocalDate date );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarModelWithPriceASC(String value, Pageable pageable,LocalDate date);


    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value and p.expireDate > :date order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarModelWithPriceDESC(String value, Pageable pageable,LocalDate date);



    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value and p.expireDate > :date order by p.postDate")
    Page<Post> findAllByStatusIsAndCarBody(@Param("value") String value, Pageable pageable,LocalDate date );

    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarBodyWithPriceASC(String value, Pageable pageable,LocalDate date);


    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value and p.expireDate > :date order by p.car.price desc ")
    Page<Post> findAllByStatusIsAndCarBodyWithPriceDESC(String value, Pageable pageable,LocalDate date);





    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value and p.expireDate > :date order by p.postDate")
    Page<Post> findAllByStatusIsAndCarTran(@Param("value") String value, Pageable pageable ,LocalDate date);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarTranWithPriceASC(@Param("value") String value, Pageable pageable ,LocalDate date);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value and p.expireDate > :date order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarTranWithPriceDESC(@Param("value") String value, Pageable pageable ,LocalDate date);





    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value and p.expireDate > :date order by p.postDate")
    Page<Post> findAllByStatusIsAndCarFuel(@Param("value") String value, Pageable pageable ,LocalDate date);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarFuelWithPriceASC(@Param("value") String value, Pageable pageable ,LocalDate date);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value and p.expireDate > :date order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarFuelWithPriceDESC(@Param("value") String value, Pageable pageable ,LocalDate date);

    Page<Post> findAllByStatus(String status, Pageable pageable);

    @Query(" select p from Post p where p.status='Approved' and p.expireDate > :date order by p.car.price asc")
    Page<Post> findAllByStatusWithPriceASC(Pageable pageable,LocalDate date);

    @Query(" select p from Post p where p.status='Approved' and p.expireDate > :date order by p.car.price desc")
    Page<Post> findAllByStatusWithPriceDESC(Pageable pageable,LocalDate date);

    @Query("select p from Post p where p.car.id = :Id and p.status='Approved' and p.expireDate > :date")
    List<Post> findPostByCarId(@Param("Id") String CarId, LocalDate date);

    @Query("select p from Post p where (p.car.name like :keyword or p.car.carDescription.make = :keyword or p.car.carDescription.model = :keyword) and p.status = 'Approved'")
    Page<Post> findByCarNameOrMakeOrModel(Pageable pageable, @Param("keyword") String keyword);

    @Query("select p from Post p where (month(p.postDate) = :month and year(p.postDate) = :year) and (p.status = 'Approved' or p.status = 'Completed')")
    List<Post> findPostsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("select p from Post p where p.status='Approved' and p.expireDate > :date and (p.car.price >= :lower and p.car.price <= :upper)")
    Page<Post> findPostsInPriceRange(Pageable pageable, @Param("lower") Long lower,@Param("upper") Long upper, @Param("date") LocalDate today);

    Page<Post> findAllByPriority(Pageable pageable, int priority);
//    SELECT *
//    FROM post
//    WHERE MONTH(post_date) = MONTH(GETDATE()) AND YEAR(post_date) = YEAR(GETDATE());


}