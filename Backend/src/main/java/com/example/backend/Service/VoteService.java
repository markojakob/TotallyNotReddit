package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.PostVoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Model.PostVote;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.PostVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final PostVoteRepository postVoteRepository;
    private final PostRepository postRepository;

    public VoteService(PostVoteRepository postVoteRepository, PostRepository postRepository) {
        this.postVoteRepository = postVoteRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public VoteResult createPostVote(PostVoteRequest request, User user) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Optional<PostVote> existingVote = postVoteRepository.findByUserAndPost(user, post);
        int voteValue = request.getVoteValue();
        int returnedVoteValue;

        if (existingVote.isPresent()) {
            PostVote oldPostVote = existingVote.get();

            if (voteValue == 0 || oldPostVote.getVoteValue() == voteValue) {
                postVoteRepository.delete(oldPostVote);
                returnedVoteValue = 0;
            } else {
                oldPostVote.setVoteValue(voteValue);
                postVoteRepository.save(oldPostVote);
                returnedVoteValue = voteValue;
            }
        } else {
            if (voteValue != 0) {
                PostVote postVote = new PostVote();
                postVote.setPost(post);
                postVote.setUser(user);
                postVote.setVoteValue(voteValue);
                postVoteRepository.save(postVote);
                returnedVoteValue = voteValue;
            } else {
                returnedVoteValue = 0;
            }
        }

        int newScore = postVoteRepository.sumVotesByPost(post);
        post.setScore(newScore);
        postRepository.save(post);

        return new VoteResult(newScore, returnedVoteValue);
    }
}