package com.duy.carshowroomdemo.service;

import com.duy.carshowroomdemo.dto.ClientDto;
import com.duy.carshowroomdemo.dto.PostDto;
import com.duy.carshowroomdemo.entity.Post;
import com.duy.carshowroomdemo.mapper.MapperManager;
import com.duy.carshowroomdemo.repository.PostRepository;
import com.duy.carshowroomdemo.util.Plan;
import com.duy.carshowroomdemo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

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

    public List<PostDto> getPostsByClientId(ClientDto clientDto, Pageable pageable){
        List<PostDto> postDtoList = new ArrayList<>();
        postRepository.findPostsByClientId(mapperManager.getClientMapper().toEntity(clientDto).getId(), pageable).forEach(x -> {
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

    public long getCarInventoryLastOffset(int size) {
        return postRepository.findAllByStatus(Status.APPROVED, PageRequest.of(0,size)).getTotalPages();
    }

    public long getLastOffset(String value, String property, int size) {
        return switch (property) {
            case "make" ->
                    postRepository.findAllByStatusIsAndCarMake(value, PageRequest.of(0, size), CURRENT_DAY).getTotalPages();
            case "body" ->
                    postRepository.findAllByStatusIsAndCarBody(value, PageRequest.of(0, size), CURRENT_DAY).getTotalPages();
            case "model" ->
                    postRepository.findAllByStatusIsAndCarModel(value, PageRequest.of(0, size), CURRENT_DAY).getTotalPages();
            case "tranmision" ->
                    postRepository.findAllByStatusIsAndCarTran(value, PageRequest.of(0, size), CURRENT_DAY).getTotalPages();
            case "fuel" ->
                    postRepository.findAllByStatusIsAndCarFuel(value, PageRequest.of(0, size), CURRENT_DAY).getTotalPages();
            default -> 0;
        };
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

    public Post findPostByCarId(String CarId){
        return postRepository.findPostByCarId(CarId, LocalDate.now()).get(0);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<PostDto> searchCar(Pageable pageable, String keyword) {
        keyword = "%" + keyword + "%";
        System.out.println("The keyword is: " + keyword);
        List<PostDto> postList = new ArrayList<>();

        postRepository.findByCarNameOrMakeOrModel(pageable, keyword)
                .forEach(post -> postList.add(mapperManager.getPostMapper().toDto(post)));

        return postList;
    }

    public Map<String, Long> getAnnualRevenue(int year) {
        Map<String, Long> revenue = new LinkedHashMap<>();
        int latestMonth = (year < Year.now().getValue()) ? 12 : LocalDate.now().getMonthValue();
        for (int i = 1; i <= latestMonth; i++){
            long monthlyRevenue = 0;
            List<Post> postList = postRepository.findPostsByMonth(i, year);
            for (Post p: postList){
                monthlyRevenue += Plan.getPrice(p.getPlan());
            }
            revenue.put(Month.of(i).toString(), monthlyRevenue);
        }

        return revenue;
    }

    public List<PostDto> getNewestPost() {
        List<PostDto> list = new ArrayList<>();
        postRepository.findPostsByMonth(LocalDate.now().getMonthValue(),LocalDate.now().getYear()).forEach(x -> list.add(mapperManager.getPostMapper().toDto(x)));
        return list;
    }

    public List<PostDto> findPostsInPriceRange(Long lower, Long upper, Pageable pageable) {
        List<PostDto> postList = new ArrayList<>();
        postRepository.findPostsInPriceRange(pageable, lower, upper, LocalDate.now())
                .forEach(post -> {
                    postList.add(mapperManager.getPostMapper().toDto(post));
                });
        return postList;
    }

    public List<PostDto> findPriorPosts() {
        List<PostDto> postList = new ArrayList<>();

        postRepository.findAllByPriority(PageRequest.of(0, 10),LocalDate.now())
                .forEach(post -> postList.add(mapperManager.getPostMapper().toDto(post)));

        return postList;
    }

    public Map<Integer, Long> getMonthlyRevenue(int m, int y) {
        Map<Integer, Long> revenue = new LinkedHashMap<>();
        int latestDate;

        if (LocalDate.now().isBefore(LocalDate.of(y,m,1))){
            return null;
        }

        if (m == Year.now().getValue() && m == Month.of(LocalDate.now().getMonthValue()).getValue()){
            latestDate = LocalDate.now().getDayOfMonth();
        } else {
            latestDate = YearMonth.of(y, m).lengthOfMonth();
        }

        for (int i = 1; i <= latestDate; i++){
            long dailyRevenue = 0;
            List<Post> postList = postRepository.findPostsByDate(i, m, y);
            for (Post p: postList){
                dailyRevenue += Plan.getPrice(p.getPlan());
            }
            revenue.put(i, dailyRevenue);
        }

        return revenue;
    }


}
