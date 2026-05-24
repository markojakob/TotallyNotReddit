// comment-service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommentResponse } from '../models/comment-response';
import { CreateCommentRequest } from '../models/comment-request';


@Injectable({ providedIn: 'root' })
export class CommentService {
  private baseUrl = 'http://localhost:8081/api/comments';

  constructor(private http: HttpClient) { }

  getByPostId(postId: number): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(`${this.baseUrl}/post/${postId}`);
  }

  createComment(request: CreateCommentRequest): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(this.baseUrl, request);
  }

  updateComment(id: number, request: { content: string }): Observable<CommentResponse> {
    return this.http.put<CommentResponse>(`${this.baseUrl}/${id}`, request);
  }

  deleteComment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}