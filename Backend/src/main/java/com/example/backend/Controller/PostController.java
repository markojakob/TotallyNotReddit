package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.RequestDtos.VoteRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.PostService;
import com.example.backend.Service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final VoteService voteService;
    private final AuthService authService;

    public PostController(PostService postService, VoteService voteService, AuthService authService) {
        this.postService = postService;
        this.voteService = voteService;
        this.authService = authService;
    }


    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody CreatePostRequest request) {
        User user = authService.getCurrentUser();
        PostResponse saved = postService.createPost(request, user);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/vote")
    public ResponseEntity<VoteResult> voteOnPost(
            @PathVariable Long postId,
            @RequestBody VoteRequest voteRequest) {

        User user = authService.getCurrentUser();
        voteRequest.setPostId(postId);
        VoteResult result = voteService.createPostVote(voteRequest, user);
        return ResponseEntity.ok(result);
    }
}