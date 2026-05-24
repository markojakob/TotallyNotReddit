package com.example.backend.Controller;

import com.example.backend.Service.PostService;
import com.example.backend.Service.SubredditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

// SearchController.java
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final PostService postService;
    private final SubredditService subredditService;

    public SearchController(PostService postService, SubredditService subredditService) {
        this.postService = postService;
        this.subredditService = subredditService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(@RequestParam String q) {
        Map<String, Object> results = new HashMap<>();
        results.put("posts", postService.searchPosts(q));
        results.put("subreddits", subredditService.searchSubreddits(q));
        return ResponseEntity.ok(results);
    }
}