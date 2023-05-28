package com.duy.carshowroomdemo.repository;

import com.duy.carshowroomdemo.entity.Client;
import com.duy.carshowroomdemo.entity.OffMeeting;
import com.duy.carshowroomdemo.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findPostsByClient(Client client);
}