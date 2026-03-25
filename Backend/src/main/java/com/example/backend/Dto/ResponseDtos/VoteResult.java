package com.example.backend.Dto.ResponseDtos;

public class VoteResult {
    private int newPostScore;   // latest post score
    private int voteValue;    // the vote the user just made

    public VoteResult(int score, int voteValue) {
        newPostScore = score;
        this.voteValue = voteValue;
    }

    public int getVoteValue() {
        return voteValue;
    }

    public int getNewPostScore() {
        return newPostScore;
    }

    public void setVoteValue(int voteValue) {
        this.voteValue = voteValue;
    }

    public void setNewPostScore(int newPostScore) {
        this.newPostScore = newPostScore;
    }
}
