import { LoginRequest } from './../../models/LoginRequest';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../../models/LoginResponse';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { UserResponse } from '../../models/UserResponse';
import { RegisterRequest } from '../../models/RegisterRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private TOKEN_KEY = 'access_token';
  private readonly apiUrl = environment.authBaseUrl;
  constructor(private http: HttpClient) { }

  login(loginReq:LoginRequest):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(this.apiUrl+"/login",loginReq)
    .pipe(
      tap((res)=>{
        localStorage.setItem(this.TOKEN_KEY,res.token);
      })
    )
  }

  signUp(register: RegisterRequest):Observable<UserResponse> {
    return this.http.post<UserResponse>(this.apiUrl+"/signup",register);
  }

  logout(): void {
  localStorage.removeItem(this.TOKEN_KEY);
  console.log('Token after logout:', localStorage.getItem(
  this.TOKEN_KEY));
  }


  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
