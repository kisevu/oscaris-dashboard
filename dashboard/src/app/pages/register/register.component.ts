import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { RegisterRequest } from '../../models/RegisterRequest';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {


  registerForm: FormGroup;
  selectedPhoto: File | null = null;

  authService = inject(AuthService);

  constructor(private fb:FormBuilder, private router: Router){
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['',[Validators.required]],
      email: ['',[Validators.required, Validators.email]],
      password: ['',[Validators.required]],
      photo: ['']
    });
  }


 onPhotoSelected(event: Event) {
  const input =  event.target as HTMLInputElement;
  if(input.files && input.files.length > 0 ){
    this.selectedPhoto = input.files[0];
  }
 }

onRegister() {
  if (this.registerForm.invalid) return;

  const { firstName, lastName, email, password } = this.registerForm.value;

  if (this.selectedPhoto) {
    const reader = new FileReader();
    reader.onload = () => {
      const base64Photo = reader.result as string;

      const registerRequest: RegisterRequest = {
        firstName,
        lastName,
        email,
        password,
        photoPath: base64Photo // base64 string
      };

      this.authService.signUp(registerRequest).subscribe({
        next: () => this.router.navigate(['/login']),
        error: (err) => console.error('Registration failed', err)
      });
    };
    reader.readAsDataURL(this.selectedPhoto); // converts to base64
  } else {
    // handle if photo is optional
    const registerRequest: RegisterRequest = {
      firstName,
      lastName,
      email,
      password,
      photoPath: ''
    };

    this.authService.signUp(registerRequest).subscribe({
      next: () => this.router.navigate(['/login']),
      error: (err) => console.error('Registration failed', err)
    });
  }
}


toLogin(){
  this.router.navigate(['/login']);
}

}
