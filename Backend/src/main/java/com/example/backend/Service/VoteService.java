package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.PostVoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Model.PostVote;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public VoteResult createPostVote(PostVoteRequest request, User user) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));

        Optional<PostVote> existingVote = voteRepository.findByUserAndPost(user, post);
        int voteValue = request.getVoteValue();
        int returnedVoteValue;

        if (existingVote.isPresent()) {
            PostVote oldPostVote = existingVote.get();

            if (voteValue == 0 || oldPostVote.getVoteValue() == voteValue) {
                voteRepository.delete(oldPostVote);
                returnedVoteValue = 0;
            } else {
                oldPostVote.setVoteValue(voteValue);
                voteRepository.save(oldPostVote);
                returnedVoteValue = voteValue;
            }
        } else {
            if (voteValue != 0) {
                PostVote postVote = new PostVote();
                postVote.setPost(post);
                postVote.setUser(user);
                postVote.setVoteValue(voteValue);
                voteRepository.save(postVote);
                returnedVoteValue = voteValue;
            } else {
                returnedVoteValue = 0;
            }
        }

        int newScore = voteRepository.sumVotesByPost(post);
        post.setScore(newScore);
        postRepository.save(post);

        return new VoteResult(newScore, returnedVoteValue);
    }
}