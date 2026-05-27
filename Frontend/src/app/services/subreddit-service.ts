import { Injectable, signal } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Subreddit } from '../models/subreddit';
import { Post } from '../models/post';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SubredditService {
  private baseApi = `${environment.apiUrl}/api/subreddits`;

  subreddits = signal<Subreddit[]>([]);

  constructor(private http: HttpClient) {}

  fetchSubreddits() {
    this.http.get<Subreddit[]>(this.baseApi).subscribe({
      next: (data) => this.subreddits.set(data),
      error: (err) => console.error('Failed to fetch subreddits', err),
    });
  }

  getById(id: number) {
    return this.http.get<Subreddit>(`${this.baseApi}/${id}`);
  }

  getByName(name: string) {
    return this.http.get<Subreddit>(`${this.baseApi}/name/${name}`);
  }

  createSubreddit(subreddit: Partial<Subreddit>) {
    return this.http.post<Subreddit>(this.baseApi, subreddit);
  }

  updateSubreddit(id: number, subreddit: Partial<Subreddit>) {
    return this.http.put<Subreddit>(`${this.baseApi}/${id}`, subreddit);
  }

  deleteSubreddit(id: number) {
    return this.http.delete<void>(`${this.baseApi}/${id}`);
  }

  getPostsBySubreddit(name: string, sort: string = 'new'): Observable<Post[]> {
  return this.http.get<Post[]>(`${this.baseApi}/${name}/posts?sort=${sort}`);
}
}
