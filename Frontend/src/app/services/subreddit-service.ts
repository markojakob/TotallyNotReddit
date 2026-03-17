import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subreddit } from '../models/subreddit';

@Injectable({
  providedIn: 'root',
})
export class SubredditService {

  private baseApi = `${environment.apiUrl}/api/subreddits`;

  constructor(private http: HttpClient) {}

  listSubreddits(): Observable<Subreddit[]> {
    return this.http.get<Subreddit[]>(this.baseApi);
  }

  getById(id: number): Observable<Subreddit> {
    return this.http.get<Subreddit>(`${this.baseApi}/${id}`);
  }

  createSubreddit(subreddit: Partial<Subreddit>): Observable<Subreddit> {
    return this.http.post<Subreddit>(this.baseApi, subreddit);
  }

  updateSubreddit(id: number, subreddit: Partial<Subreddit>): Observable<Subreddit> {
    return this.http.put<Subreddit>(`${this.baseApi}/${id}`, subreddit);
  }

  deleteSubreddit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseApi}/${id}`);
  }
}