package com.example.backend.Repository;


import com.example.backend.Model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    boolean existsByNameAndIdNot(String name, Long id);

    Optional<Subreddit> findByName(String name);

    @Query("SELECT s FROM Subreddit s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Subreddit> searchByName(@Param("query") String query);
}
