package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Exception.ForbiddenException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.PostMapper;
import com.example.backend.Model.Post;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.SubredditRepository;
import com.example.backend.Repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    public PostService(PostRepository postRepository,
                       SubredditRepository subredditRepository,
                       VoteRepository voteRepository,
                       AuthService authService) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.voteRepository = voteRepository;
        this.authService = authService;
    }

    private Integer getCurrentUserVote(Post post) {
        try {
            User currentUser = authService.getCurrentUser();
            return voteRepository.findByUserAndPost(currentUser, post)
                    .map(vote -> vote.getVoteValue())
                    .orElse(0);
        } catch (Exception e) {
            return 0;
        }
    }

    public PostResponse createPost(CreatePostRequest request, User author) {
        Subreddit subreddit = subredditRepository.findById(request.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        Post post = PostMapper.fromRequest(request, author, subreddit);
        postRepository.save(post);

        return PostMapper.toResponse(post);
    }

    public List<PostResponse> getPostsBySubreddit(String name) {
        Subreddit subreddit = subredditRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));

        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(post -> PostMapper.toResponse(post, getCurrentUserVote(post)))
                .toList();
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> PostMapper.toResponse(post, getCurrentUserVote(post)))
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        return PostMapper.toResponse(post, getCurrentUserVote(post));
    }

    public PostResponse updatePost(Long id, CreatePostRequest request, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You can only edit your own posts");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);

        return PostMapper.toResponse(post, getCurrentUserVote(post));
    }

    public void deletePost(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }

    public List<PostResponse> getPostsByUser(User user) {
        return postRepository.findAllByUser(user)
                .stream()
                .map(post -> PostMapper.toResponse(post, getCurrentUserVote(post)))
                .toList();
    }
}