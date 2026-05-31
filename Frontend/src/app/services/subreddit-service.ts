import { Injectable, computed, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Subreddit } from '../models/subreddit';
import { Post } from '../models/post';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SubredditService {
  private baseApi = `${environment.apiUrl}/api/subreddits`;

  // -------------------------
  // STATE (SIGNALS)
  // -------------------------

  private _subreddits = signal<Subreddit[]>([]);
  private _loading = signal(false);
  private _error = signal<string | null>(null);
  private _joinedSubreddits = signal<Subreddit[]>([]);

  readonly joinedSubreddits = this._joinedSubreddits.asReadonly();
  readonly subreddits = this._subreddits.asReadonly();
  readonly loading = this._loading.asReadonly();
  readonly error = this._error.asReadonly();


  readonly subredditCount = computed(() => this._subreddits().length);

  constructor(private http: HttpClient) {}

  private setLoading(value: boolean) {
    this._loading.set(value);
  }

  private setError(message: string | null) {
    this._error.set(message);
  }

  private setSubreddits(data: Subreddit[]) {
    this._subreddits.set(data);
  }

  fetchSubreddits(): void {
    this.setLoading(true);
    this.setError(null);

    this.http.get<Subreddit[]>(this.baseApi).subscribe({
      next: (data) => {
        this.setSubreddits(data);
        this.setLoading(false);
      },
      error: (err) => {
        this.setError('Failed to load subreddits');
        this.setLoading(false);
        console.error(err);
      },
    });
  }


  getById(id: number): Observable<Subreddit> {
    return this.http.get<Subreddit>(`${this.baseApi}/${id}`);
  }

  getByName(name: string): Observable<Subreddit> {
    return this.http.get<Subreddit>(`${this.baseApi}/name/${name}`);
  }

  getPostsBySubreddit(name: string, sort: string = 'new'): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseApi}/${name}/posts?sort=${sort}`);
  }

  searchSubreddits(query: string): Observable<Subreddit[]> {
    return this.http.get<Subreddit[]>(`${this.baseApi}/search?q=${query}`);
  }

  getJoinedSubreddits(): Observable<Subreddit[]> {
    return this.http.get<Subreddit[]>(`${this.baseApi}/joined`);
  }

  createSubreddit(subreddit: Partial<Subreddit>): Observable<Subreddit> {
    return this.http.post<Subreddit>(this.baseApi, subreddit).pipe(
      tap((created) => {
        this._subreddits.update((list) => [created, ...list]);
      })
    );
  }

  updateSubreddit(id: number, subreddit: Partial<Subreddit>): Observable<Subreddit> {
    return this.http.put<Subreddit>(`${this.baseApi}/${id}`, subreddit).pipe(
      tap((updated) => {
        this._subreddits.update((list) =>
          list.map((s) => (s.id === id ? updated : s))
        );
      })
    );
  }

  deleteSubreddit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseApi}/${id}`).pipe(
      tap(() => {
        this._subreddits.update((list) =>
          list.filter((s) => s.id !== id)
        );
      })
    );
  }

  fetchJoinedSubreddits(): void {
  this.http.get<Subreddit[]>(`${this.baseApi}/joined`).subscribe({
    next: (data) => this._joinedSubreddits.set(data),
    error: (err) => console.error('Failed to fetch joined subreddits', err)
  });
}

joinSubreddit(id: number): Observable<void> {
  return this.http.post<void>(`${this.baseApi}/${id}/join`, {}).pipe(
    tap(() => {
      this._subreddits.update(list =>
        list.map(s => s.id === id ? { ...s, isJoined: true, membersCount: (s.membersCount ?? 0) + 1 } : s)
      );
    })
  );
}

leaveSubreddit(id: number): Observable<void> {
  return this.http.post<void>(`${this.baseApi}/${id}/leave`, {}).pipe(
    tap(() => {
      this._subreddits.update(list =>
        list.map(s => s.id === id ? { ...s, isJoined: false, membersCount: Math.max((s.membersCount ?? 1) - 1, 0) } : s)
      );
      this._joinedSubreddits.update(list => list.filter(s => s.id !== id));
    })
  );
}
}