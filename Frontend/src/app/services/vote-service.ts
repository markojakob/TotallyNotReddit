import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth-service';
import { LoginPromptService } from './login-prompt-service';
import { VoteRequest } from '../models/vote-request';
import { VoteResult } from '../models/vote-result';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class VoteService {
  private baseApi = `${environment.apiUrl}/api/posts`;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private loginPromptService: LoginPromptService
  ) {}

  handleVote(postId: number, currentVote: number, clickedValue: number): Observable<VoteResult> | null {
    if (!this.authService.isAuthenticated()) {
      this.loginPromptService.show();
      return null;
    }

    const targetValue = currentVote === clickedValue ? 0 : clickedValue;

    const request: VoteRequest = {
      postId,
      userId: 0,
      voteValue: targetValue as (1 | -1 | 0)
    };

    return this.http.post<VoteResult>(`${this.baseApi}/${postId}/vote`, request);
  }
}