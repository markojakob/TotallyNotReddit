package com.example.backend.Mapper;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.RequestDtos.CreateUserRequest;
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

    public static User toUser(CreateUserRequest request ){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        return user;
    }
}