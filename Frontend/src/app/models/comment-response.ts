export interface CommentResponse {
  id: number;
  content: string;
  username: string;
  postId: number;
  createdAt: string;
  score: number;
  currentUserVote: number;
}