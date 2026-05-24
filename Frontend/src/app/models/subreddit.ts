// src/app/models/subreddit.ts
export interface Subreddit {
  id: number;
  name: string;
  description: string | null;
  createdById: number | null;  
  createdByUsername: string | null; 
  rules: string | null;
  isPrivate: boolean;
  createdAt: string;   
  postsCount?: number;         
}