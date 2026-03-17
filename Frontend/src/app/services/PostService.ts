import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post} from '../models/post';
import { VoteRequest } from '../models/vote-request';
import { VoteResponse } from '../models/vote-response';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private apiUrl = environment.apiUrl;
  private baseApi = this.apiUrl + "/api/posts";

  constructor(private http: HttpClient) {}

  listPosts(): Observable<Post[]>{
    return this.http.get<Post[]>(this.baseApi);
  }

getById(id: number): Observable<Post> {
  return this.http.get<Post>(`${this.baseApi}/${id}`);
}

createPost(post: Partial<Post>): Observable<Post> {
  return this.http.post<Post>(this.baseApi, post);
}

updatePost(id: Number, post: Partial<Post>): Observable<Post> {
  return this.http.put<Post>(`${this.baseApi}/${id}`, post);
}

deletePost(id: Number): Observable<Post> {
  return this.http.delete<Post>(`${this.baseApi}/${id}`);
}

voteOnPost(postId: number, vote: VoteRequest): Observable<VoteResponse> {
  vote.postId = postId;
  return this.http.post<VoteResponse>(`${this.baseApi}/${postId}/vote`, vote);
}

getPostsBySubreddit(subredditId: number): Observable<Post[]> {
  return this.http.post<Post[]>(`${this.baseApi}/${subredditId}/posts`, subredditId);
}

  }

