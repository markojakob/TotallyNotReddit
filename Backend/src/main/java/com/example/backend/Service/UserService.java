package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreateUserRequest;
import com.example.backend.Dto.ResponseDtos.UserResponse;
import com.example.backend.Exception.BadRequestException;
import com.example.backend.Exception.DuplicateResourceException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.Model.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("An user with this email already exist");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse updateUser(Long id, CreateUserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), id)) {
            throw new BadRequestException("Username already exists");
        }
        existing.setUsername(request.getUsername());

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BadRequestException("An user with this email already exist");
        }
        existing.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(existing);
        return UserMapper.toResponse(existing);
    }

    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(existing);
    }

    public void validateUserDoesNotExist(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username is already taken.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("An account with this email already exists.");
        }
    }

}