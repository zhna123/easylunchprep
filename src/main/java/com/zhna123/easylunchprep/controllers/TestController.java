package com.zhna123.easylunchprep.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path = "/test")
    public String helloWorld() {
        return "Test succeed!";
    }
}
