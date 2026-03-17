// src/app/models/vote-response.ts
export interface VoteResponse {
  id: number;
  postId: number;
  userId: number;
  voteValue: 1 | -1;
  createdAt: string;
}