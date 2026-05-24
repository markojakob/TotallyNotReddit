package com.example.backend.Repository;

import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Model.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    Optional<PostVote> findByUserAndPost(User user, Post post);

    @Query("SELECT COALESCE(SUM(v.voteValue), 0) FROM PostVote v WHERE v.post = :post")
    int sumVotesByPost(@Param("post") Post post);
}
