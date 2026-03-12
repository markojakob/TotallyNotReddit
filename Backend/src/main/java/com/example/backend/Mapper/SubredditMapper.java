package com.example.backend.Mapper;

import com.example.backend.Dto.SubredditDto;
import com.example.backend.model.Subreddit;

public class SubredditMapper {
    public static SubredditDto toDto(Subreddit subreddit) {
        return new SubredditDto(
                subreddit.getId(),
                subreddit.getName(),
                subreddit.getDescription(),
                subreddit.getCreatedBy() != null ? subreddit.getCreatedBy().getId() : null,
                subreddit.getCreatedAt()
        );
    }
}
