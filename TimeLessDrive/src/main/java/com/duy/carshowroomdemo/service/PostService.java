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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private final MapperManager mapperManager = MapperManager.getInstance();
    private final LocalDate CURRENT_DAY = LocalDate.now();

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
    public long getLastOffset(String value, String property, int size) {
        switch (property){
            case"make":
                return postRepository.findAllByStatusIsAndCarMake(value,PageRequest.of(0, size),CURRENT_DAY).getTotalPages();
            case"body":
                return postRepository.findAllByStatusIsAndCarBody(value,PageRequest.of(0, size),CURRENT_DAY).getTotalPages();
            case"model":
                return postRepository.findAllByStatusIsAndCarModel(value,PageRequest.of(0, size),CURRENT_DAY).getTotalPages();
            case"tranmision":
                return postRepository.findAllByStatusIsAndCarTran(value,PageRequest.of(0, size),CURRENT_DAY).getTotalPages();
            case"fuel":
                return postRepository.findAllByStatusIsAndCarFuel(value,PageRequest.of(0, size),CURRENT_DAY).getTotalPages();
        }
        return  0 ;
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
    public List<PostDto> getApprovedPostsByStatus(Pageable pageable, String property, String direction) {

        List<PostDto> postDtoList = new ArrayList<>();
        if(property.equalsIgnoreCase("price")) {
            if(direction.equalsIgnoreCase("asc")){
                postRepository.findAllByStatusWithPriceASC(pageable,CURRENT_DAY).forEach(x -> {
                    postDtoList.add(mapperManager.getPostMapper().toDto(x));
                });
            }else{
                postRepository.findAllByStatusWithPriceDESC(pageable,CURRENT_DAY).forEach(x -> {
                    postDtoList.add(mapperManager.getPostMapper().toDto(x));
                });
            }
        }
        return postDtoList;

    }

    public List<PostDto> searchApprovedCarByMake(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarMake(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }
    public List<PostDto> searchOrderedApprovedCarByMake(String value, Pageable pageable,String direction) {

        List<PostDto>postDtoList = new ArrayList<>();

        if(direction.equalsIgnoreCase("asc")){
        postRepository.findAllByStatusIsAndCarMakeWithPriceASC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }else{
            postRepository.findAllByStatusIsAndCarMakeWithPriceDESC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postDtoList;
    }

    public List<PostDto> searchApprovedCarByModel(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarModel(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }
    public List<PostDto> searchOrderedApprovedCarByModel(String value, Pageable pageable, String direction) {
        List<PostDto>postDtoList = new ArrayList<>();
        if(direction.equalsIgnoreCase("asc")){
            postRepository.findAllByStatusIsAndCarModelWithPriceASC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        } else{
            postRepository.findAllByStatusIsAndCarModelWithPriceDESC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postDtoList;
    }

    public List<PostDto> searchApprovedCarByBody(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarBody(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }
    public List<PostDto> searchOrderedApprovedCarByBody(String value, Pageable pageable, String direction) {
        List<PostDto>postDtoList = new ArrayList<>();
        if(direction.equalsIgnoreCase("asc")){
            postRepository.findAllByStatusIsAndCarBodyWithPriceASC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }else{
            postRepository.findAllByStatusIsAndCarBodyWithPriceDESC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postDtoList;

    }

    public List<PostDto> searchApprovedCarByTran(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarTran(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }
    public List<PostDto> searchOrderedApprovedCarByTran(String value, Pageable pageable, String direction) {
        List<PostDto>postDtoList = new ArrayList<>();
        if(direction.equalsIgnoreCase("asc")){

            postRepository.findAllByStatusIsAndCarTranWithPriceASC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }else{
            postRepository.findAllByStatusIsAndCarTranWithPriceDESC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postDtoList;
    }



    public List<PostDto> searchApprovedCarByFuel(String value, Pageable pageable) {
        List<PostDto>postDtoList = new ArrayList<>();
        postRepository.findAllByStatusIsAndCarFuel(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        return postDtoList;
    }


    public List<PostDto> searchOrderedApprovedCarByFuel(String value, Pageable pageable, String direction) {
        List<PostDto>postDtoList = new ArrayList<>();
        if(direction.equalsIgnoreCase("asc")){
            postRepository.findAllByStatusIsAndCarFuelWithPriceASC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));

        }else{

            postRepository.findAllByStatusIsAndCarFuelWithPriceDESC(value,pageable,CURRENT_DAY).forEach(x-> postDtoList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postDtoList;
    }

    public List<PostDto> findSortedApprovedPosts(Pageable pageable, String direction){
        List<PostDto> postList = new ArrayList<>();
        if(direction.equalsIgnoreCase("asc")){
            postRepository.findAllByStatusWithPriceASC(pageable,CURRENT_DAY).forEach(x -> postList.add(mapperManager.getPostMapper().toDto(x)));
        }else{
            postRepository.findAllByStatusWithPriceDESC(pageable,CURRENT_DAY).forEach(x -> postList.add(mapperManager.getPostMapper().toDto(x)));
        }
        return postList;
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
