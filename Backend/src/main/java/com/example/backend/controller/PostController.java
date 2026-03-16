package com.example.backend.controller;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.RequestDtos.VoteRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Dto.ResponseDtos.VoteResponse;
import com.example.backend.Mapper.VoteMapper;
import com.example.backend.model.Vote;
import com.example.backend.service.PostService;
import com.example.backend.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final VoteService voteService;

    public PostController(PostService postService, VoteService voteService) {
        this.postService = postService;
        this.voteService = voteService;
    }


    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody CreatePostRequest request) {
        Long loggedUserId = 1L;
        PostResponse saved = postService.createPost(request, loggedUserId);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{postId}/vote")
    public ResponseEntity<VoteResponse> voteOnPost(
            @PathVariable Long postId,
            @RequestBody VoteRequest vote) {

        vote.setPostId(postId);
        VoteResponse savedVote = voteService.createPostVote(vote);
        return ResponseEntity.ok(savedVote);
    }
}