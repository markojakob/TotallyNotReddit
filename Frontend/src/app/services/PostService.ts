import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post} from '../models/post';
import { VoteRequest } from '../models/vote-request';
import { VoteResponse } from '../models/vote-response';
import { VoteResult } from '../models/vote-result';

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

  getForYouFeed(): Observable<Post[]> {
  return this.http.get<Post[]>(`${environment.apiUrl}/api/posts/feed`);
}

getById(id: number): Observable<Post> {
  return this.http.get<Post>(`${this.baseApi}/${id}`);
}

createPost(data: { title: string; content: string; subredditId: number; mediaUrl?: string | null }) {
  return this.http.post<Post>(`${this.baseApi}`, data);
}

updatePost(id: Number, post: Partial<Post>): Observable<Post> {
  return this.http.put<Post>(`${this.baseApi}/${id}`, post);
}

deletePost(id: Number): Observable<Post> {
  return this.http.delete<Post>(`${this.baseApi}/${id}`);
}


}