package com.example.backend.Repository;

import com.example.backend.Model.Comment;
import com.example.backend.Model.CommentVote;
import com.example.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findByUserAndComment(User user, Comment comment);

    @Query("SELECT COALESCE(SUM(v.voteValue), 0) FROM CommentVote v WHERE v.comment = :comment")
    int sumVotesByComment(Comment comment);
}