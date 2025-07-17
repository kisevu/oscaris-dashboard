import { Component, inject, OnInit } from '@angular/core';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import { CompanyService } from '../../services/company/company.service';
import { AsyncPipe, CommonModule } from '@angular/common';
import { CombineService } from '../../services/combine/combine.service';
@Component({
  selector: 'app-dashboard-card',
  standalone: true,
  imports: [AsyncPipe,MatProgressBarModule,MatCardModule,MatChipsModule, CommonModule],
  templateUrl: './dashboard-card.component.html',
  styleUrl: './dashboard-card.component.css'
})
export class DashboardCardComponent  implements OnInit{


  companyService = inject(CompanyService);
  combinedService = inject(CombineService);

  companys$ = this.companyService.getCompanies();

  employeesAndCompanies$ = this.combinedService.employeesAndCompanies$;


  ngOnInit(): void {
  }


}
