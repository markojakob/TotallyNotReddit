package com.example.backend.Dto;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Integer karma;


    public UserDto() {}

    public UserDto(Long id, String username, String email, Integer karma) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.karma = karma;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getKarma() {
        return karma;
    }
    public void setKarma(Integer karma) {
        this.karma = karma;
    }
}