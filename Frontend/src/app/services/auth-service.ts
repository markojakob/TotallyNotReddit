import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs'; // Added tap
import { environment } from '../../environments/environment';

export interface RegisterDto {
  username: string;
  email: string;
  password: string;
}

export interface loginDto {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated = signal<boolean>(!!localStorage.getItem('token'));
  private baseApi = `${environment.apiUrl}/api/auth`;

  constructor(private http: HttpClient) {}

  register(dto: RegisterDto): Observable<any> {
    return this.http.post(`${this.baseApi}/register`, dto);
  }

  login(dto: loginDto): Observable<any> {
  return this.http.post<any>(`${this.baseApi}/login`, dto).pipe(
    tap((res) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('username', res.username);

      this.isAuthenticated.set(true);
    })
  );
}

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.isAuthenticated.set(false);
  }
}