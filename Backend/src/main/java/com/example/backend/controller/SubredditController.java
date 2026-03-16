package com.example.backend.controller;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    public SubredditController(SubredditService subredditService) {
        this.subredditService = subredditService;
    }

    @GetMapping
    public ResponseEntity<List<SubredditResponse>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAllSubreddits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditResponse> getSubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(subredditService.getSubredditById(id));
    }

    @PostMapping
    public ResponseEntity<SubredditResponse> createSubreddit(
            @RequestBody CreateSubredditRequest request) {

        Long loggedUserId = 1L;
        SubredditResponse saved = subredditService.createSubreddit(request, loggedUserId);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubredditResponse> updateSubreddit(
            @PathVariable Long id,
            @RequestBody CreateSubredditRequest request) {

        SubredditResponse updated = subredditService.updateSubreddit(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteSubreddit(id);
        return ResponseEntity.noContent().build();
    }
}