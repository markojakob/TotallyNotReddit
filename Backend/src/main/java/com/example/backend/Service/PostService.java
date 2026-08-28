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
import com.example.backend.Repository.SubredditMemberRepository;
import com.example.backend.Repository.SubredditRepository;
import com.example.backend.Repository.PostVoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final PostVoteRepository postVoteRepository;
    private final AuthService authService;
    private final SubredditMemberRepository memberRepository;

    public PostService(PostRepository postRepository,
                       SubredditRepository subredditRepository,
                       PostVoteRepository postVoteRepository,
                       AuthService authService, SubredditMemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
        this.postVoteRepository = postVoteRepository;
        this.authService = authService;
        this.memberRepository = memberRepository;
    }

    private Integer getCurrentUserVote(Post post) {
        try {
            User currentUser = authService.getCurrentUser();
            return postVoteRepository.findByUserAndPost(currentUser, post)
                    .map(vote -> vote.getVoteValue())
                    .orElse(0);
        } catch (Exception e) {
            return 0;
        }
    }

    public PostResponse createPost(CreatePostRequest request, User author) {
        Subreddit subreddit = subredditRepository.findById(request.getSubredditId())
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));

        Post post = PostMapper.fromRequest(request, author, subreddit);
        postRepository.save(post);

        return PostMapper.toResponse(post);
    }

    public List<PostResponse> getPostsBySubreddit(String name, String sort) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));

        Sort sorting = switch (sort) {
            case "top" -> Sort.by(Sort.Direction.DESC, "score");
            case "new" -> Sort.by(Sort.Direction.DESC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        return postRepository.findAllBySubreddit(subreddit, sorting)
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
        post.setMediaUrl(request.getMediaUrl());
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

    @Transactional
    public List<PostResponse> getPostsForUser(User user) {
        List<Subreddit> joinedSubreddits = memberRepository.findSubredditsByUser(user);
        if (joinedSubreddits.isEmpty()) return List.of();

        return postRepository.findAllBySubredditIn(joinedSubreddits)
                .stream()
                .map(post -> PostMapper.toResponse(post, getCurrentUserVote(post)))
                .toList();
    }



    public List<PostResponse> searchPosts(String query) {
        return postRepository.searchByTitleOrContent(query)
                .stream()
                .map(post -> PostMapper.toResponse(post, getCurrentUserVote(post)))
                .toList();
    }



}