package com.duy.carshowroomdemo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler extends RuntimeException{
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(){
        return new ModelAndView("views/user/error-page");
    }
}
