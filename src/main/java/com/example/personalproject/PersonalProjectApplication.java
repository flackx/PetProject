package com.example.personalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PersonalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalProjectApplication.class, args);
    }

}
