package com.example.backend.Repository;

import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndPost(User user, Post post);
}
