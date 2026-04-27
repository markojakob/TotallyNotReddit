import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface RegisterDto {
  username: string;
  email: string;
  password: string;
}

export interface loginDto {
  username: string,
  password: string
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated = signal<boolean>(!!localStorage.getItem('token'));
  private baseApi = `${environment.apiUrl}/api/auth`

  constructor(private http: HttpClient) {}

  register(dto: RegisterDto): Observable<any> {
    return this.http.post(`${this.baseApi}/register`, dto);
  }

  login(dto: loginDto): Observable<any> {
    this.isAuthenticated.set(true);
    return this.http.post(`${this.baseApi}/login`, dto);
  }
logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.isAuthenticated.set(false);
  }
}