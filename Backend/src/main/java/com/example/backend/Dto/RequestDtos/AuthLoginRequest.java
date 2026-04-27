package com.example.backend.Dto.RequestDtos;

public class AuthLoginRequest {
    private String username;
    private String password;

    public AuthLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}


