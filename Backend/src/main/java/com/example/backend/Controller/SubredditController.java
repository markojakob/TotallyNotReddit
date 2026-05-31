package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.PostService;
import com.example.backend.Service.SubredditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;
    private final PostService postService;
    private final AuthService authService;

    public SubredditController(SubredditService subredditService, PostService postService, AuthService authService) {
        this.subredditService = subredditService;
        this.postService = postService;
        this.authService = authService;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<SubredditResponse> createSubreddit(
            @Valid @RequestBody CreateSubredditRequest request) {

        User user = authService.getCurrentUser();
        SubredditResponse saved = subredditService.createSubreddit(request, user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{name}/posts")
    public List<PostResponse> getPostsBySubreddit(
            @PathVariable String name,
            @RequestParam(defaultValue = "new") String sort) {
        return postService.getPostsBySubreddit(name, sort);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<SubredditResponse> updateSubreddit(
            @PathVariable Long id,
            @Valid @RequestBody CreateSubredditRequest request) {

        SubredditResponse updated = subredditService.updateSubreddit(id, request);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubreddit(@PathVariable Long id) {
        subredditService.deleteSubreddit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubredditResponse>> searchSubreddits(@RequestParam String q) {
        return ResponseEntity.ok(subredditService.searchSubreddits(q));
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        subredditService.joinSubreddit(id, user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leave(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        subredditService.leaveSubreddit(id, user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/joined")
    public ResponseEntity<List<SubredditResponse>> getJoined() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(subredditService.getJoinedSubreddits(user));
    }
}