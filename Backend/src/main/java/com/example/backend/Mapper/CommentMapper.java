package com.example.backend.Mapper;

import com.example.backend.Dto.RequestDtos.CreateCommentRequest;
import com.example.backend.Dto.ResponseDtos.CommentResponse;
import com.example.backend.Model.Comment;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;

public class CommentMapper {

    public static Comment fromRequest(CreateCommentRequest request, User author, Post post) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(author);
        comment.setPost(post);
        return comment;
    }

    public static CommentResponse toResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setPostId(comment.getPost().getId());
        response.setCreatedAt(comment.getCreatedAt());
        response.setScore(comment.getScore());

        if (comment.getUser() != null) {
            response.setUsername(comment.getUser().getUsername());
        }
        return response;
    }
}