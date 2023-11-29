package com.example.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public Map<String, String> test(){
        Map<String, String> map = new HashMap<>();
        map.put("key", "hello world");
        return map;
    }

    @GetMapping("/authTest")
    public Map<String, String> authTest(){
        Map<String, String> map = new HashMap<>();
        map.put("key", "Auth test");
        return map;
    }

}
