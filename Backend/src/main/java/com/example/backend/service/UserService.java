package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<User> getAsync(Long id) {
        return CompletableFuture.completedFuture(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User by the id of " + id + " does not exist"))
        );
    }

    @Async
    public CompletableFuture<List<User>> listAsync() {
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @Async
    public CompletableFuture<User> createAsync(User user) {
        return CompletableFuture.completedFuture(userRepository.save(user));
    }

    @Async
    public CompletableFuture<User> updateAsync(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User by the id of " + id + " does not exist"));

        if (userRepository.existsByUsernameAndIdNot(user.getUsername(), existingUser.getId())) {
            throw new RuntimeException("A user with this username already exists");
        }
        existingUser.setUsername(user.getUsername());

        if (userRepository.existsByEmailAndIdNot(user.getEmail(), existingUser.getId())) {
            throw new RuntimeException("A user with this email already exists");
        }
        existingUser.setEmail(user.getEmail());

        return CompletableFuture.completedFuture(userRepository.save(existingUser));
    }

    @Async
    public CompletableFuture<User> deleteAsync(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User by the id of " + id + " does not exist"));
        userRepository.deleteById(id);
        return CompletableFuture.completedFuture(user);
    }
}
