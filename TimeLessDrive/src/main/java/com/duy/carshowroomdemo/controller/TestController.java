package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.CarImageDto;
import com.duy.carshowroomdemo.entity.Car;
import com.duy.carshowroomdemo.entity.CarImage;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private Service service;

    @RequestMapping("")
    public String showCarForm(){
        return "views/car-form";
    }

    @RequestMapping("/post")
    public String testPostCar(@RequestParam("myFile") MultipartFile file){
        Car car = service.getCarService().findCarByName("Car for testing");
        CarImage carImage = new CarImage();
        try {
            carImage.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        carImage.setCar(car);

        car.getCarImageList().add(carImage);
        service.getCarService().save(car);

//        service.getImageService().save(image);

        return showCarForm();
    }

    @RequestMapping(value = "/see", produces = MediaType.IMAGE_JPEG_VALUE)
    public String showImage(Model model){

        CarImageDto imageDto = service.getCarImageService().findById("1");
        model.addAttribute("image", imageDto);

        return "views/result";
    }

    @RequestMapping("/show/{carName}")
    public String showCarImage(Model model, @PathVariable String carName){

        Car carForTesting = service.getCarService().findCarByName(carName);
        model.addAttribute("car", carForTesting);

        return "views/show-car-image";


    }

    @RequestMapping("/show/id={imageId}")
    public String showCarImageNext(Model model, @PathVariable String imageId){

        CarImageDto image = service.getCarImageService().findById(imageId);
        model.addAttribute("image", image);

        return "views/single-image";

    }

    @GetMapping("/display-image")
    public void displayImage(HttpServletResponse response) throws IOException {
        CarImageDto image = service.getCarImageService().findById("1");
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image.getContent());
        response.getOutputStream().close();
    }
    @RequestMapping("/test-buy-package")
    public String testBuyPackage(){
        return "views/BuyPackage";
    }
}

