package com.example.jwt.dto;

public class ProfileResponse {
    private String username;
    private String message;

    public ProfileResponse(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
