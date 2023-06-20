package com.example.personalproject;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EnableJpaRepositories(basePackages = "com.example.personalproject.repository")
@EntityScan(basePackages = "com.example.personalproject.model")
class PersonalProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
