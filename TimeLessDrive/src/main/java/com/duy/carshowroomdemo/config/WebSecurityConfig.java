package com.duy.carshowroomdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity htppSecurity) throws Exception{
        return htppSecurity
                .csrf().disable()
                .authorizeHttpRequests(authorization -> {
//                    authorization.requestMatchers("/**").permitAll();
//                    authorization.requestMatchers("/staff/**").hasRole("STAFF");
//                    authorization.requestMatchers("/").permitAll();
                    authorization.anyRequest().permitAll();
                })
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer.loginPage("/sign-in");
                    httpSecurityOAuth2LoginConfigurer.defaultSuccessUrl("/google-handler", true);
//                    httpSecurityOAuth2LoginConfigurer.userInfoEndpoint();
                })
                .build();
    }
}
