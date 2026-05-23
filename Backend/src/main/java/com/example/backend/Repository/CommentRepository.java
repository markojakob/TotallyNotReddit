package com.example.backend.Repository;

import com.example.backend.Model.Comment;
import com.example.backend.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>
{
    List<Comment> findAllByPost(Post post);
}
