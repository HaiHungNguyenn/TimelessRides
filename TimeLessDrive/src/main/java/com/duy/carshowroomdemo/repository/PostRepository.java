package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String>{
    Page<Post> findPostsByClient(Client client, Pageable pageable);

    List<Post> findAllByStatusIs(String status);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.make = :value order by p.postDate")
    Page<Post> findAllByStatusIsAndCarMake(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.make = :value order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarMakeWithPriceASC(String value, Pageable pageable);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.make = :value order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarMakeWithPriceDESC(String value, Pageable pageable);




    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value order by p.postDate")
    Page<Post> findAllByStatusIsAndCarModel(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarModelWithPriceASC(String value, Pageable pageable);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.model = :value order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarModelWithPriceDESC(String value, Pageable pageable);





    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value order by p.postDate")
    Page<Post> findAllByStatusIsAndCarBody(@Param("value") String value, Pageable pageable );

    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarBodyWithPriceASC(String value, Pageable pageable);
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.body = :value order by p.car.price desc ")
    Page<Post> findAllByStatusIsAndCarBodyWithPriceDESC(String value, Pageable pageable);







    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value order by p.postDate")
    Page<Post> findAllByStatusIsAndCarTran(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarTranWithPriceASC(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.transmission = :value order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarTranWithPriceDESC(@Param("value") String value, Pageable pageable );





    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value order by p.postDate")
    Page<Post> findAllByStatusIsAndCarFuel(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value order by p.car.price asc")
    Page<Post> findAllByStatusIsAndCarFuelWithPriceASC(@Param("value") String value, Pageable pageable );
    @Query(" select p from Post p where p.status='Approved' and p.car.carDescription.fuelType = :value order by p.car.price desc")
    Page<Post> findAllByStatusIsAndCarFuelWithPriceDESC(@Param("value") String value, Pageable pageable );

    Page<Post> findAllByStatus(String status, Pageable pageable);

    @Query(" select p from Post p where p.status='Approved' order by p.car.price asc")
    Page<Post> findAllByStatusWithPriceASC(Pageable pageable);

    @Query(" select p from Post p where p.status='Approved' order by p.car.price desc")
    Page<Post> findAllByStatusWithPriceDESC(Pageable pageable);



}