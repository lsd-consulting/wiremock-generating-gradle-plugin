package com.lsdconsulting.stub.integration.controller;

import com.lsdconsulting.stub.integration.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/root1")
public class RestControllerExample {

    @GetMapping("/resource1")
    public Greeting resource1() {
        return Greeting.builder().name("name").build();
    }
}
