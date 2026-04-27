import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth-service';
import { Router, RouterLink } from "@angular/router";

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  
  registerForm: FormGroup;
  successMessage: string | null = null;
  isSubmitting = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

onSubmit() {
  const formValue = this.registerForm.value;
  this.authService.register(formValue).subscribe({
    next: (res: any) => {
      console.log('Registration successful:', res);
      this.successMessage = `Account created for ${res.userName}! Redirecting to login...`;
      this.registerForm.reset();

      setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      
    },
    error: (err) => console.error('Registration error:', err)
  });
}
}
