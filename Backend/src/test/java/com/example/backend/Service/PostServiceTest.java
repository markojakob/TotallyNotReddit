package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.SubredditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private SubredditRepository subredditRepository;

    @Test
    public void createPostShouldCreateCorrectPost() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Subreddit subreddit = new Subreddit();
        subreddit.setId(1L);

        CreatePostRequest request = new CreatePostRequest();
        request.setSubredditId(1L);
        request.setTitle("Test title");
        request.setContent("Test content");

        when(subredditRepository.findById(1L)).thenReturn(Optional.of(subreddit));

        // Act
        PostResponse post = postService.createPost(request, user);

        // Assert
        assertNotNull(post);
    }
}