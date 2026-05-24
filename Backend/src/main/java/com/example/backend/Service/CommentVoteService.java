package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CommentVoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Model.Comment;
import com.example.backend.Model.CommentVote;
import com.example.backend.Model.User;
import com.example.backend.Repository.CommentRepository;
import com.example.backend.Repository.CommentVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;

    public CommentVoteService(CommentVoteRepository commentVoteRepository,
                              CommentRepository commentRepository) {
        this.commentVoteRepository = commentVoteRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public VoteResult createCommentVote(CommentVoteRequest request, User user) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        Optional<CommentVote> existingVote = commentVoteRepository.findByUserAndComment(user, comment);
        int voteValue = request.getVoteValue();
        int returnedVoteValue;

        if (existingVote.isPresent()) {
            CommentVote oldVote = existingVote.get();
            if (voteValue == 0 || oldVote.getVoteValue() == voteValue) {
                commentVoteRepository.delete(oldVote);
                returnedVoteValue = 0;
            } else {
                oldVote.setVoteValue(voteValue);
                commentVoteRepository.save(oldVote);
                returnedVoteValue = voteValue;
            }
        } else {
            if (voteValue != 0) {
                CommentVote newVote = new CommentVote();
                newVote.setComment(comment);
                newVote.setUser(user);
                newVote.setVoteValue(voteValue);
                commentVoteRepository.save(newVote);
                returnedVoteValue = voteValue;
            } else {
                returnedVoteValue = 0;
            }
        }

        int newScore = commentVoteRepository.sumVotesByComment(comment);
        comment.setScore(newScore);
        commentRepository.save(comment);

        return new VoteResult(newScore, returnedVoteValue);
    }
}