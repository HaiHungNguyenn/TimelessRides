package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.PostRepository;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<PostDto> getPostsByClient(ClientDto clientDto, int offset, int size){
        List<PostDto> postDtoList = new ArrayList<>();
        postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(clientDto), PageRequest.of(offset, size)).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });
        return postDtoList;
    }

    public List<PostDto> getPostsPerPage(int offSet, int size){
        List<PostDto> postDtoList = new ArrayList<>();

        postRepository.findAllByStatusIs(Status.PENDING, PageRequest.of(offSet, size))
                .forEach((x) -> postDtoList.add(mapperManager.getPostMapper().toDto(x)));

//        postRepository.findAll(PageRequest.of(offSet, size)).forEach((x) -> {
//            postDtoList.add(mapperManager.getPostMapper().toDto(x));
//        });

        return postDtoList;
    }

    public List<PostDto> getPostSortedPerPage(int offset, int size, String property, String direction) {
        List<PostDto> postDtoList = new ArrayList<>();

        Sort.Direction sortDirection = (direction.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;

        postRepository.findAllByStatusIs(Status.PENDING, PageRequest.of(offset, size, Sort.by(sortDirection, property))).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });

        return postDtoList;
    }

    public long getTotalPostRequests() {
        return postRepository.findAllByStatusIs(Status.PENDING).size();
    }

    public long getLastOffset(int size) {
        return postRepository.findAllByStatusIs(Status.PENDING, PageRequest.of(0,size)).getTotalPages();
    }

    public long getLastOffset(ClientDto clientDto, int size) {
        int totalElements =  postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(clientDto),PageRequest.of(0, size)).size();
        return (long) Math.ceil((double) totalElements / size);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElse(null);
    }
}
