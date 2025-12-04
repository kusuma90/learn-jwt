package com.example.jwt.controller;

import com.example.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String username =request.get("username");
        String password = request.get("password");

        if(username.equals("admin") && password.equals("1234")){
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token",token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");

    }
}
