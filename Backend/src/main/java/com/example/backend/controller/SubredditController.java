package com.example.backend.controller;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.service.PostService;
import com.example.backend.service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;
    private final PostService postService;

    public SubredditController(SubredditService subredditService, PostService postService) {
        this.subredditService = subredditService;
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<SubredditResponse>> getAllSubreddits() {
        return ResponseEntity.ok(subredditService.getAllSubreddits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditResponse> getSubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(subredditService.getSubredditById(id));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<SubredditResponse> getByName(@PathVariable String name) {
        return ResponseEntity.ok(subredditService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<SubredditResponse> createSubreddit(
            @RequestBody CreateSubredditRequest request) {

        Long loggedUserId = 1L;
        SubredditResponse saved = subredditService.createSubreddit(request, loggedUserId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{name}/posts")
    public List<PostResponse> getPostsBySubreddit (@PathVariable String name) {
        return postService.getPostsBySubreddit(name);
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