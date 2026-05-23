export interface VoteRequest {
    postId: number;
  userId: number;
  voteValue: 1 | -1 | 0; 
}