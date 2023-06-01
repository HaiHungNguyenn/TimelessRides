package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private MapperManager mapperManager = new MapperManager();

    public List<PostDto> getAllPostRequest() {
        List<PostDto> postRequestList = new ArrayList<>();
        postRepository.findAll().forEach(x -> {
            postRequestList.add(mapperManager.getPostMapper().toDto(x));
        });
        return postRequestList;
    }

    public List<PostDto> getPostsByClient(ClientDto clientDto){
        List<PostDto> postDtoList = new ArrayList<>();
        postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(clientDto)).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });
        return postDtoList;
    }
}
