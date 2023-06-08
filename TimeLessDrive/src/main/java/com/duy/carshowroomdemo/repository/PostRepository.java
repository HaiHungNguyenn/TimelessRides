package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, String>{
    Page<Post> findPostsByClient(Client client, Pageable pageable);

    Page<Post> findAllByStatusIs(String status, Pageable pageable);

    List<Post> findAllByStatusIs(String status);
}