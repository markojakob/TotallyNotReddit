import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth-service';
import { LoginPromptService } from './login-prompt-service';
import { VoteResult } from '../models/vote-result';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class VoteService {
  private baseApi = `${environment.apiUrl}/api/votes`;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loginPromptService: LoginPromptService
  ) {}

  handlePostVote(postId: number, currentVote: number, clickedValue: number): Observable<VoteResult> | null {
    if (!this.authService.isAuthenticated()) {
      this.loginPromptService.show();
      return null;
    }

    const targetValue = currentVote === clickedValue ? 0 : clickedValue;

    return this.http.post<VoteResult>(`${this.baseApi}/post`, {
      postId,
      userId: 0,
      voteValue: targetValue
    });
  }

  handleCommentVote(commentId: number, currentVote: number, clickedValue: number): Observable<VoteResult> | null {
    if (!this.authService.isAuthenticated()) {
      this.loginPromptService.show();
      return null;
    }

    const targetValue = currentVote === clickedValue ? 0 : clickedValue;

    return this.http.post<VoteResult>(`${this.baseApi}/comment`, {
      commentId,
      voteValue: targetValue
    });
  }
}