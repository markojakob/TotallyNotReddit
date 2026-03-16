import { Post } from "./post";

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  createdAt: string;
  updatedAt: string | null;
  isAdmin: boolean;
  karma: number;
  posts: Post[];
}