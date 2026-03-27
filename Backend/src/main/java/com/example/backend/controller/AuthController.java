package com.example.backend.controller;

import com.example.backend.Dto.RequestDtos.AuthLoginRequest;
import com.example.backend.Dto.RequestDtos.AuthRegisterRequest;
import com.example.backend.Dto.ResponseDtos.AuthResponse;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
