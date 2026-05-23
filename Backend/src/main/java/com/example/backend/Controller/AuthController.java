package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.AuthLoginRequest;
import com.example.backend.Dto.RequestDtos.AuthRegisterRequest;
import com.example.backend.Dto.ResponseDtos.AuthResponse;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
