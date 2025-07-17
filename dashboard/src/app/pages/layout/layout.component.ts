import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';
import { NgOptimizedImage } from '@angular/common';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, FooterComponent, NgOptimizedImage],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css',
})
export class LayoutComponent {


  authService = inject(AuthService);
  router = inject(Router);
  private TOKEN_KEY = 'access_token';


  onLogout():void {
    this.authService.logout();
    if(localStorage.getItem(this.TOKEN_KEY) === null ){
      console.log(`Token has been deleted!`);
      this.router.navigate(['/login']);
    }
  }

}
