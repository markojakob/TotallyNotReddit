package com.example.backend.Mapper;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Model.Post;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;

public class PostMapper {

    public static Post fromRequest(CreatePostRequest request, User user, Subreddit subreddit) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        post.setSubreddit(subreddit);
        return post;
    }

    public static PostResponse toResponse(Post post, Integer currentUserVote) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser() != null ? post.getUser().getId() : null,
                post.getUser() != null ? post.getUser().getUsername() : null,
                post.getSubreddit() != null ? post.getSubreddit().getId() : null,
                post.getSubreddit() != null ? post.getSubreddit().getName() : null,
                post.getScore(),
                post.getMediaUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                currentUserVote != null ? currentUserVote : 0
        );
    }

    public static PostResponse toResponse(Post post) {
        return toResponse(post, 0);
    }
}