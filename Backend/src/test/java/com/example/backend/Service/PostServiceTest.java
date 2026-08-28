package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Exception.ForbiddenException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Model.Post;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.SubredditRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private SubredditRepository subredditRepository;

    @InjectMocks
    private AuthService authService;

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
        assertEquals("Test title", post.getTitle());
        assertEquals("Test content", post.getContent());
        assertEquals(1L, post.getSubredditId());


    }

    @Test
    public void createPostShouldThrowWhenSubredditNotFound() {
        // Arrange
        User user = new User();
        user.setId(1L);


        CreatePostRequest request = new CreatePostRequest();
        request.setSubredditId(1L);
        request.setTitle("Test title");
        request.setContent("Test content");

        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> {
            postService.createPost(request, user);
        });
    }

    @Test
    public void updatePostShouldThrowWhenPostNotFound() {
        // Arrange
        Long id = 43L;
        CreatePostRequest request = new CreatePostRequest();
        User currentUser = new User();
        currentUser.setId(1L);

        // Assert
        assertThrows(NotFoundException.class, () -> {
           postService.updatePost(id, request, currentUser);
        });
    }

    @Test
    public void updatePostShouldThrowForbiddenExceptionWhenUserIsForbidden() {
        // Arrange
        CreatePostRequest request = new CreatePostRequest();
        User currentUser = new User();
        User user = new User();
        user.setId(2L);
        Post post = new Post();
        post.setId(1L);
        currentUser.setId(1L);
        post.setUser(user);

        // Act
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // Assert
        assertThrows(ForbiddenException.class, () -> {
            postService.updatePost(post.getId(), request, currentUser);
        });
    }

    @Test
    public void updatePostShouldReturnUpdatedPostWhenDataCorrect() {
        // Arrange
        CreatePostRequest request = new CreatePostRequest();
        User currentUser = new User();
        currentUser.setId(1L);
        Post post = new Post();
        post.setId(1L);
        post.setUser(currentUser);

        // Act
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        PostResponse response = postService.updatePost(post.getId(), request, currentUser);

        // Assert
        assertEquals(post.getId(), response.getId());
        assertEquals(post.getTitle(), response.getTitle());
    }

    @Test
    public void deletePostsShouldThrowWHenPostIdIsIncorrect() {
        // Arrange

        Long postId = 21L;
        Post post = new Post();
        post.setId(1L);

        User user = new User();
        user.setId(1L);

        // Assert
        assertThrows(NotFoundException.class, () -> {
           postService.deletePost(postId, user);
        });
    }

    @Test
    public void deletePostsShouldThrowWHenUserIsForbidden() {
        // Arrange
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);
        User curentUser = new User();
        curentUser.setId(2L);
        post.setUser(curentUser);

        // Act
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        assertThrows(ForbiddenException.class, () -> {
           postService.deletePost(post.getId(), user);
        });
    }

    @Test
    public void deletePostsShouldDeleteWhenDataIsCorrect() {
        // Arrange
        Post post = new Post();
        post.setId(1L);
        User curentUser = new User();
        curentUser.setId(2L);
        post.setUser(curentUser);

        // Act
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        postService.deletePost(post.getId(), curentUser);

        verify(postRepository).delete(post);
    }

    @Test
    public void getPostsByUserShouldReturnAListOfPosts() {
        // Arrange
        Post post = new Post();
        post.setId(1L);
        post.setTitle("First post");
        Post post2 = new Post();
        post2.setId(2L);
        User user = new User();
        List<Post> posts = List.of(post, post2);

        when(postRepository.findAllByUser(user)).thenReturn(posts);

        List<PostResponse> response = postService.getPostsByUser(user);
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
    }

}