package com.example.backend.controller;

import com.example.backend.Dto.RequestDtos.CreateUserRequest;
import com.example.backend.Dto.ResponseDtos.UserResponse;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PreAuthorize("principal.isAdmin == true")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("principal.isAdmin == true")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("principal.isAdmin == true")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }

    @PreAuthorize("principal.isAdmin == true")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PreAuthorize("principal.isAdmin == true")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
