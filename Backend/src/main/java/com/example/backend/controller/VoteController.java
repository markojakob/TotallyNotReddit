package com.example.backend.controller;

import com.example.backend.Dto.VoteDto;
import com.example.backend.Dto.VoteRequest;
import com.example.backend.Mapper.VoteMapper;
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
    public CompletableFuture<ResponseEntity<VoteDto>> getVote(@PathVariable Long id) {
        return voteService.getAsync(id)
                .thenApply(vote -> ResponseEntity.ok(VoteMapper.toDto(vote)));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<VoteDto>>> listVotes() {
        return voteService.listAsync()
                .thenApply(votes ->
                        ResponseEntity.ok(
                                votes.stream()
                                        .map(VoteMapper::toDto)
                                        .toList()
                        )
                );
    }

}