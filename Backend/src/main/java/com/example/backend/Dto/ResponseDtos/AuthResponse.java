package com.example.backend.Dto.ResponseDtos;

public class AuthResponse {
    private String token;
    private String userName;
    private String email;

    public AuthResponse(String token, String userName, String email) {
        this.token = token;
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
