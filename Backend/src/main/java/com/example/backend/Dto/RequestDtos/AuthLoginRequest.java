package com.example.backend.Dto.RequestDtos;

public class AuthLoginRequest {
    private String userName;
    private String password;

    public AuthLoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}


