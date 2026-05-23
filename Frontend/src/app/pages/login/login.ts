import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  successMessage: string | null = null; // Cleaned up 'String' to lowercase primitive 'string'
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.errorMessage = null; 
    this.successMessage = null;

    this.authService.login(this.loginForm.value).subscribe({
      next: (res: any) => {
        setTimeout(() => {
          this.successMessage = `Welcome ${res.username}!`;
        }, 0);
        setTimeout(() => {
          this.router.navigate(['/']);
        }, 1500);
      },
      error: (err) => {
        console.error(err);
        setTimeout(() => {
          this.errorMessage = 'Invalid credentials';
        }, 0);
      }
    });
  }
}