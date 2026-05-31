import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginDto } from '../models/loginDto';
import { RegisterDto } from '../models/registerDto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly username = signal<string | null>(localStorage.getItem('username'));
  readonly isAuthenticated = signal<boolean>(!!localStorage.getItem('token'));
  readonly currentUsername = computed(() => this.username());

  private baseApi = `${environment.apiUrl}/api/auth`;

  constructor(private http: HttpClient) {}

  register(dto: RegisterDto): Observable<any> {
    return this.http.post(`${this.baseApi}/register`, dto);
  }

  login(dto: LoginDto): Observable<any> {
    return this.http.post<any>(`${this.baseApi}/login`, dto).pipe(
      tap((res) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('username', res.username);
        this.username.set(res.username);
        this.isAuthenticated.set(true);
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.username.set(null);
    this.isAuthenticated.set(false);
  }

  getUsername(): string | null {
    return this.username();
  }
}