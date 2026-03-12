package com.example.backend.Mapper;

import com.example.backend.Dto.VoteDto;
import com.example.backend.model.Vote;

public class VoteMapper {
    public static VoteDto toDto(Vote vote) {
        return new VoteDto(
                vote.getId(),
                vote.getPost() != null ? vote.getPost().getId() : null,
                vote.getUser() != null ? vote.getUser().getId() : null,
                vote.getVoteValue(),
                vote.getCreatedAt()
        );
    }
}