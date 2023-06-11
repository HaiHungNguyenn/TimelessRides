package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.CarDto;
import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.PostRepository;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<PostDto> getPostsByClient(ClientDto clientDto, Pageable pageable){
        List<PostDto> postDtoList = new ArrayList<>();
        postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(clientDto), pageable).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });
        return postDtoList;
    }

    public List<PostDto> getPostsPerPage(Pageable pageable){
        List<PostDto> postDtoList = new ArrayList<>();

        postRepository.findAllByStatus(Status.PENDING, pageable)
                .forEach((x) -> postDtoList.add(mapperManager.getPostMapper().toDto(x)));

        return postDtoList;
    }

    public List<PostDto> getPostSortedPerPage(Pageable pageable) {
        List<PostDto> postDtoList = new ArrayList<>();

        postRepository.findAllByStatus(Status.PENDING, pageable).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });

        return postDtoList;
    }

    public long getTotalPostRequests() {
        return postRepository.findAllByStatusIs(Status.PENDING).size();
    }

    public long getLastOffset(int size) {
        return postRepository.findAllByStatus(Status.PENDING, PageRequest.of(0,size)).getTotalPages();
    }

    public long getLastOffset(ClientDto clientDto, int size) {
        return postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(clientDto),PageRequest.of(0, size)).getTotalPages();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Post findById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public long getTotalPostRequests(ClientDto client) {
        return postRepository.findPostsByClient(mapperManager.getClientMapper().toEntity(client),PageRequest.of(0, 1)).getTotalElements();
    }

//    public List<PostDto> getPostsByStatus(String status,Pageable pageable) {
////        List<PostDto> postDtoList = new ArrayList<>();
////
////        postRepository.findAllByStatusIs(Status.PENDING, pageable).forEach(x -> {
////            postDtoList.add(mapperManager.getPostMapper().toDto(x));
////        });
//
////        return postDtoList;
//        List<PostDto> postDtoList = new ArrayList<>();
//        postRepository.findAllByStatusIs(status,pageable).forEach(x->{postDtoList.add(mapperManager.getPostMapper().toDto(x));});
//
//        return postDtoList;
//
//    }

    public List<PostDto> getApprovedPostsByStatus(Pageable pageable) {
                List<PostDto> postDtoList = new ArrayList<>();

        postRepository.findAllByStatus(Status.APPROVED, pageable).forEach(x -> {
            postDtoList.add(mapperManager.getPostMapper().toDto(x));
        });
        return postDtoList;
    }

    public List<PostDto> searchApprovedCarByMake(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarMake(value,pageable).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }

    public List<PostDto> searchApprovedCarByModel(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarModel(value,pageable).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }

    public List<PostDto> searchApprovedCarByBody(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarBody(value,pageable).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }
}
