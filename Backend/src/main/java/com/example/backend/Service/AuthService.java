package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.AuthLoginRequest;
import com.example.backend.Dto.RequestDtos.AuthRegisterRequest;
import com.example.backend.Dto.ResponseDtos.AuthResponse;
import com.example.backend.Exception.UnauthorizedException;
import com.example.backend.Model.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public AuthResponse register(AuthRegisterRequest request) {

        userService.validateUserDoesNotExist(request.getUsername(), request.getEmail());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

    public AuthResponse login(AuthLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new UnauthorizedException("Invalid username or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getEmail());
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new UnauthorizedException("No authenticated user found");
        }
        return (User) auth.getPrincipal();
    }

}
