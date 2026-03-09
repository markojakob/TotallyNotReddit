package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get a single user by id
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> getUser(@PathVariable Long id) {
        return userService.getAsync(id)
                .thenApply(ResponseEntity::ok);
    }

    // Get all users
    @GetMapping
    public CompletableFuture<ResponseEntity<List<User>>> listUsers() {
        return userService.listAsync()
                .thenApply(ResponseEntity::ok);
    }

    // Create a new user
    @PostMapping
    public CompletableFuture<ResponseEntity<User>> createUser(@RequestBody User user) {
        return userService.createAsync(user)
                .thenApply(ResponseEntity::ok);
    }

    // Update a user
    @PutMapping
    public CompletableFuture<ResponseEntity<User>> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        return userService.updateAsync(id, user)
                .thenApply(ResponseEntity::ok);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<User>> deleteUser(@PathVariable Long id) {
        return userService.deleteAsync(id)
                .thenApply(ResponseEntity::ok);
    }
}