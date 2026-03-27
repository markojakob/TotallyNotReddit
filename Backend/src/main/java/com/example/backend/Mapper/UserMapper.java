package com.example.backend.Mapper;

import com.example.backend.Dto.ResponseDtos.UserResponse;
import com.example.backend.Model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getIsAdmin(),
                user.getKarma()
        );
    }
}