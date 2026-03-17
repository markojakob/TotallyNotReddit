// src/app/models/subreddit.ts
export interface Subreddit {
  id: number;
  name: string;
  description: string | null;
  createdById: number | null;   // backend createdBy.id
  createdByUsername: string | null; // backend createdBy.username
  rules: string | null;
  isPrivate: boolean;
  createdAt: string;            // ISO date string from backend
  postsCount?: number;          // optional: number of posts
}