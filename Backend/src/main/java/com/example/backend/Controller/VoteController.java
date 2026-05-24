package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.CommentVoteRequest;
import com.example.backend.Dto.RequestDtos.PostVoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.CommentVoteService;
import com.example.backend.Service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;
    private final CommentVoteService commentVoteService;
    private final AuthService authService;

    public VoteController(VoteService voteService,
                          CommentVoteService commentVoteService,
                          AuthService authService) {
        this.voteService = voteService;
        this.commentVoteService = commentVoteService;
        this.authService = authService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post")
    public ResponseEntity<VoteResult> voteOnPost(@RequestBody PostVoteRequest request) {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(voteService.createPostVote(request, user));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment")
    public ResponseEntity<VoteResult> voteOnComment(@RequestBody CommentVoteRequest request) {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(commentVoteService.createCommentVote(request, user));
    }
}