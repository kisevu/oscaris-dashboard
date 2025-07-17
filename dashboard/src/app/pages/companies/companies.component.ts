import { Component } from '@angular/core';
import { CompanyComponent } from '../../components/company/company.component';

@Component({
  selector: 'app-companies',
  standalone: true,
  imports: [CompanyComponent],
  templateUrl: './companies.component.html',
  styleUrl: './companies.component.css',
})
export class CompaniesComponent {}
