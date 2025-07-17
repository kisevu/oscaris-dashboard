import { Component } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { Router, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../models/LoginRequest';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  standalone: true
})
export class LoginComponent {

  email = '';
  password = '';
  errorMessage = '';


  constructor(private auth: AuthService, private router: Router) {}

  onLogin(): void {
    this.errorMessage = '';
    const loginRequest:LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.auth.login(loginRequest).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Invalid email or password';
        this.router.navigate(['/login']);
      }
    });
  }

    toRegister() {
      this.router.navigate(['/register']);
    }
}
