package com.duy.carshowroomdemo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity htppSecurity) throws Exception{
//        return htppSecurity
//                .authorizeHttpRequests(authorization -> {
//                    authorization.requestMatchers("/staff/**").permitAll();
//                    authorization.anyRequest().authenticated();
//                })
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }
}
