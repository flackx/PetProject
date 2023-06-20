package com.example.personalproject.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String sayHello() {
        return "The app is running!";
    }
}
