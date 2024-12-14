package com.hotelApp.configClass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilter {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().disable();
//        http.authorizeHttpRequests().anyRequest().permitAll();
        http.authorizeHttpRequests().requestMatchers("").permitAll();

        return http.build();  // Create a new SecurityFilterChain object and return it.

    }
}

