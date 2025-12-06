package com.example.jwt.controller;

import com.example.jwt.dto.ProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/profile")
    public ProfileResponse getProfile(Authentication authentication){
        String username = authentication.getName();
        return new ProfileResponse(
                username, " This is your protected profile data");
    }
}
