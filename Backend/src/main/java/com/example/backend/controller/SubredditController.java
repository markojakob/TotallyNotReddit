package com.example.backend.controller;

import com.example.backend.model.Subreddit;
import com.example.backend.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Subreddit>> getSubreddit(@PathVariable Long id) {
        return subredditService.getAsync(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Subreddit>>> listSubreddits() {
        return subredditService.listAsync()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Subreddit>> createSubreddit(
            @RequestBody Subreddit subreddit,
            @RequestParam Long userId) {
        return subredditService.createAsync(subreddit, userId)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Subreddit>> updateSubreddit(
            @PathVariable Long id,
            @RequestBody Subreddit subreddit) {
        return subredditService.updateAsync(id, subreddit)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Subreddit>> deleteSubreddit(@PathVariable Long id) {
        return subredditService.deleteAsync(id)
                .thenApply(ResponseEntity::ok);
    }
}