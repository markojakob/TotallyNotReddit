package com.example.backend.Dto.RequestDtos;

public class AuthRegisterRequest {
    private String username;
    private String password;
    private String email;

    public AuthRegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
