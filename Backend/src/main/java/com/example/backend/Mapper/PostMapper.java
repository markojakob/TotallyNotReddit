package com.example.backend.Mapper;

import com.example.backend.Dto.PostDto;
import com.example.backend.model.Post;

public class PostMapper {

    public static PostDto toDto(Post post) {
        Long userId = null;
        String username = null;
        if (post.getUser() != null) {
            userId = post.getUser().getId();
            username = post.getUser().getUsername();
        }

        Long subredditId = null;
        String subredditName = null;
        if (post.getSubreddit() != null) {
            subredditId = post.getSubreddit().getId();
            subredditName = post.getSubreddit().getName();
        }

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                userId,
                username,
                subredditId,
                subredditName,
                post.getScore(),
                post.getMediaUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}