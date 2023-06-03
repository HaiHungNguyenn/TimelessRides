package com.duy.carshowroomdemo.controller;

import com.duy.carshowroomdemo.dto.CarImageDto;
import com.duy.carshowroomdemo.entity.CarImage;
import com.duy.carshowroomdemo.service.Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class ImageHandler {
    @Autowired
    private Service service;

    @RequestMapping("/image-resolver/{id}")
    public void resolveImage(@PathVariable String id, HttpServletResponse response) throws IOException {

        CarImageDto image = service.getCarImageService().findById(id);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(image.getContent());
        response.getOutputStream().close();
    }
}
