package com.example.backend.controller;

import com.example.backend.Dto.PostDto;
import com.example.backend.Dto.VoteDto;
import com.example.backend.Dto.VoteRequest;
import com.example.backend.Mapper.PostMapper;
import com.example.backend.Mapper.VoteMapper;
import com.example.backend.model.Post;
import com.example.backend.model.Vote;
import com.example.backend.service.PostService;
import com.example.backend.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final VoteService voteService;

    public PostController(PostService postService, VoteService voteService) {
        this.postService = postService;
        this.voteService = voteService;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<PostDto>> getPost(@PathVariable Long id) {
        return postService.getAsync(id)
                .thenApply(post -> ResponseEntity.ok(PostMapper.toDto(post)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<PostDto>>> listPosts() {
        return postService.listAsync()
                .thenApply(posts ->
                        ResponseEntity.ok(
                                posts.stream()
                                        .map(PostMapper::toDto)
                                        .toList()
                        )
                );
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<PostDto>> createPost(@RequestBody PostDto post) {
        return postService.createAsync(post)
                .thenApply(saved -> ResponseEntity.ok(PostMapper.toDto(saved)));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<PostDto>> updatePost(
            @PathVariable Long id,
            @RequestBody Post post) {

        return postService.updateAsync(id, post)
                .thenApply(updated -> ResponseEntity.ok(PostMapper.toDto(updated)));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<PostDto>> deletePost(@PathVariable Long id) {
        return postService.deleteAsync(id)
                .thenApply(deleted -> ResponseEntity.ok(PostMapper.toDto(deleted)));
    }



    @PostMapping("/{postId}/vote")
    public CompletableFuture<ResponseEntity<VoteDto>> voteOnPost(
            @PathVariable Long postId,
            @RequestBody VoteDto voteDto) {

        return CompletableFuture.supplyAsync(() -> {
            Vote savedVote = voteService.createPostVote(
                    postId,
                    voteDto.getVoteValue(),
                    voteDto.getUserId()
            );

            return ResponseEntity.ok(VoteMapper.toDto(savedVote));
        });
    }
}