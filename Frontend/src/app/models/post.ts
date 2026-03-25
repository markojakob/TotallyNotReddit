export interface Post {
  id: number;
  title: string;
  content: string;

  userId: number;
  username: string | null;

  subredditId: number;
  subredditName: string | null;

  score: number;
  mediaUrl: string | null;

  createdAt: string;       
  updatedAt: string | null;
  loading?: boolean; 
}