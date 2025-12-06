package com.example.jwt.controller;

import com.example.jwt.dto.LoginResponse;
import com.example.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    //@CrossOrigin(origins = "*", allowCredentials = "true")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){
        String username =request.get("username");
        String password = request.get("password");

        if(username.equals("admin") && password.equals("1234")){
            String accessToken = jwtService.generateAccessToken(username);
            String refreshToken = jwtService.generateRefreshToken(username);

            ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                    .httpOnly(true)
                    .secure(false) //true in production
                    .path("/auth/refresh")
                    .maxAge(7*24*60*60)
                    .build();

            //return ResponseEntity.ok(Map.of("token",token));
            return  ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new LoginResponse(accessToken));
        }
        return ResponseEntity.status(401).body("Invalid credentials");

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken){
        try{
            String username = jwtService.generateRefreshToken(refreshToken);
            String newAccessToken = jwtService.generateAccessToken(username);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
