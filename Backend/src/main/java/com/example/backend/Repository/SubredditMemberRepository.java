package com.example.backend.Repository;

import com.example.backend.Model.Subreddit;
import com.example.backend.Model.SubredditMember;
import com.example.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubredditMemberRepository extends JpaRepository<SubredditMember, Long> {
    Optional<SubredditMember> findByUserAndSubreddit(User user, Subreddit subreddit);
    boolean existsByUserAndSubreddit(User user, Subreddit subreddit);
    int countBySubreddit(Subreddit subreddit);
    List<SubredditMember> findAllByUser(User user);

    @Query("SELECT sm.subreddit FROM SubredditMember sm WHERE sm.user = :user")
    List<Subreddit> findSubredditsByUser(@Param("user") User user);
}