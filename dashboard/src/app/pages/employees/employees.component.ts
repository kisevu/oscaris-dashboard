import { NgOptimizedImage } from '@angular/common';
import { Component, inject } from '@angular/core';
import { EmployeeComponent } from '../../components/employee/employee.component';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [NgOptimizedImage,RouterLink],
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.css',
})
export class EmployeesComponent  {

  router = inject(Router);

  employees() {
    this.router.navigate(['/employees/details'])
    }


}
