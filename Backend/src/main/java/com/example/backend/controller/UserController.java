package com.example.backend.controller;

import com.example.backend.Dto.UserDto;
import com.example.backend.Mapper.UserMapper;
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

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserDto>> getUser(@PathVariable Long id) {
        return userService.getAsync(id)
                .thenApply(user -> ResponseEntity.ok(UserMapper.toDto(user)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserDto>>> listUsers() {
        return userService.listAsync()
                .thenApply(users -> users.stream()
                        .map(UserMapper::toDto)
                        .toList())
                .thenApply(ResponseEntity::ok);
    }


    @PostMapping
    public CompletableFuture<ResponseEntity<UserDto>> createUser(@RequestBody User user) {
        return userService.createAsync(user)
                .thenApply(savedUser -> ResponseEntity.ok(UserMapper.toDto(savedUser)));
    }


    @PutMapping
    public CompletableFuture<ResponseEntity<UserDto>> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        return userService.updateAsync(id, user)
                .thenApply(updatedUser -> ResponseEntity.ok(UserMapper.toDto(updatedUser)));
    }


    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteAsync(id)
                .thenApply(deletedUser -> ResponseEntity.noContent().build());
    }
}