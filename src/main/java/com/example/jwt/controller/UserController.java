package com.example.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/profile")
    public String profile(Principal principal){
        return "Hello" +principal.getName()+ "this is your profile!";
    }
}
