// comment-service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CommentResponse {
  id: number;
  content: string;
  username: string;
  createdAt: string;
}

export interface CreateCommentRequest {
  postId: number;
  content: string;
}

@Injectable({ providedIn: 'root' })
export class CommentService {
  private baseUrl = 'http://localhost:8081/api/comments';

  constructor(private http: HttpClient) {}

  getByPostId(postId: number): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(`${this.baseUrl}/post/${postId}`);
  }

  createComment(request: CreateCommentRequest): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(this.baseUrl, request);
  }
}