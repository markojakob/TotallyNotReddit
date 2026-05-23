package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreateCommentRequest;
import com.example.backend.Dto.ResponseDtos.CommentResponse;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.CommentMapper;
import com.example.backend.Model.Comment;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Repository.CommentRepository;
import com.example.backend.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        return commentRepository.findAllByPost(post)
                .stream()
                .map(CommentMapper::toResponse)
                .toList();
    }

    public CommentResponse createComment(CreateCommentRequest request, User author) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Comment comment = CommentMapper.fromRequest(request, author, post);
        commentRepository.save(comment);

        return CommentMapper.toResponse(comment);
    }
}