package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.RequestDtos.PostVoteRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.PostService;
import com.example.backend.Service.VoteService;
import jakarta.validation.Valid;
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
           @Valid @RequestBody CreatePostRequest request) {
        User user = authService.getCurrentUser();
        PostResponse saved = postService.createPost(request, user);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/vote")
    public ResponseEntity<VoteResult> voteOnPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostVoteRequest postVoteRequest) {
        System.out.println(">>> VoteService HIT userId=" + postVoteRequest.getUserId() +
                " postId=" + postVoteRequest.getPostId() +
                " value=" + postVoteRequest.getVoteValue());

        User user = authService.getCurrentUser();
        postVoteRequest.setPostId(postId);
        VoteResult result = voteService.createPostVote(postVoteRequest, user);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody CreatePostRequest request) {
        User user = authService.getCurrentUser();
        PostResponse updated = postService.updatePost(id, request, user);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        postService.deletePost(id, user);
        return ResponseEntity.noContent().build();
    }
}