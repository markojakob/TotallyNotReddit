package com.example.backend.controller;

import com.example.backend.Dto.SubredditDto;
import com.example.backend.Mapper.SubredditMapper;
import com.example.backend.model.Subreddit;
import com.example.backend.model.User;
import com.example.backend.service.SubredditService;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;
    private final UserService userService;

    public SubredditController(SubredditService subredditService, UserService userService) {
        this.subredditService = subredditService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SubredditDto>> getSubreddit(@PathVariable Long id) {
        return subredditService.getAsync(id)
                .thenApply(subreddit -> ResponseEntity.ok(SubredditMapper.toDto(subreddit)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<SubredditDto>>> listSubreddits() {
        return subredditService.listAsync()
                .thenApply(subreddits ->
                        ResponseEntity.ok(
                                subreddits.stream()
                                        .map(SubredditMapper::toDto)
                                        .toList()
                        )
                );
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<SubredditDto>> createSubreddit(
            @RequestBody Subreddit subreddit) {

        return subredditService.createAsync(subreddit)
                .thenApply(saved -> ResponseEntity.ok(SubredditMapper.toDto(saved)));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<SubredditDto>> updateSubreddit(
            @PathVariable Long id,
            @RequestBody Subreddit subreddit) {
        return subredditService.updateAsync(id, subreddit)
                .thenApply(updated -> ResponseEntity.ok(SubredditMapper.toDto(updated)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<SubredditDto>> deleteSubreddit(@PathVariable Long id) {
        return subredditService.deleteAsync(id)
                .thenApply(deleted -> ResponseEntity.ok(SubredditMapper.toDto(deleted)));
    }
}
