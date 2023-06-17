package com.duy.carshowroomdemo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class Email {
    private String from;
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> properties;
}
