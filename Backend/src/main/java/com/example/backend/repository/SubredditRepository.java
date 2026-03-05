package com.example.backend.repository;

import com.example.backend.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    boolean existsByNameAndIdNot(String name, Long id);
}
