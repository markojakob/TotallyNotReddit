package com.example.backend.Mapper;

import com.example.backend.Dto.UserDto;
import com.example.backend.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getKarma()
        );
    }
}