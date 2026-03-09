package com.example.backend.controller;

import com.example.backend.model.Vote;
import com.example.backend.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Vote>> getVote(@PathVariable Long id) {
        return voteService.getAsync(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Vote>>> listVotes() {
        return voteService.listAsync()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/{postId}/vote")
    public ResponseEntity<Vote> voteOnPost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody Vote vote) {

        Vote result = voteService.createPostVote(
                postId,
                userId,
                vote.getVoteValue()
        );

        return ResponseEntity.ok(result);
    }
}