package com.example.backend.Controller;

import com.example.backend.Dto.RequestDtos.CreateCommentRequest;
import com.example.backend.Dto.ResponseDtos.CommentResponse;
import com.example.backend.Model.User;
import com.example.backend.Service.AuthService;
import com.example.backend.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final AuthService authService;

    public CommentController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping
    public CommentResponse createComment(@Valid @RequestBody CreateCommentRequest request) {
        User currentUser = authService.getCurrentUser();
        return commentService.createComment(request, currentUser);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id,
                                         @RequestBody CreateCommentRequest request) {
        User currentUser = authService.getCurrentUser();
        return commentService.updateComment(id, request, currentUser);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        User currentUser = authService.getCurrentUser();
        commentService.deleteComment(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}