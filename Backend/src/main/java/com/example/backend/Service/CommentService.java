package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreateCommentRequest;
import com.example.backend.Dto.ResponseDtos.CommentResponse;
import com.example.backend.Exception.ForbiddenException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.CommentMapper;
import com.example.backend.Model.Comment;
import com.example.backend.Model.CommentVote;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Repository.CommentRepository;
import com.example.backend.Repository.CommentVoteRepository;
import com.example.backend.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentVoteRepository commentVoteRepository;
    private final AuthService authService;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          CommentVoteRepository commentVoteRepository,
                          AuthService authService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentVoteRepository = commentVoteRepository;
        this.authService = authService;
    }

    private int getCurrentUserVote(Comment comment) {
        try {
            User currentUser = authService.getCurrentUser();
            return commentVoteRepository.findByUserAndComment(currentUser, comment)
                    .map(CommentVote::getVoteValue)
                    .orElse(0);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        return commentRepository.findAllByPost(post)
                .stream()
                .map(comment -> CommentMapper.toResponse(comment, getCurrentUserVote(comment)))
                .toList();
    }

    public CommentResponse createComment(CreateCommentRequest request, User author) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Comment comment = CommentMapper.fromRequest(request, author, post);
        commentRepository.save(comment);

        return CommentMapper.toResponse(comment, 0);
    }

    public CommentResponse updateComment(Long id, CreateCommentRequest request, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You can only edit your own comments");
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return CommentMapper.toResponse(comment, getCurrentUserVote(comment));
    }

    public void deleteComment(Long id, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You can only delete your own comments");
        }

        commentRepository.delete(comment);
    }
}