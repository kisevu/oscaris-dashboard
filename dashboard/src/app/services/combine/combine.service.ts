import { inject, Injectable } from '@angular/core';
import { combineLatest, map } from 'rxjs';
import { CompanyService } from '../company/company.service';
import { EmployeesService } from '../employees/employees.service';

@Injectable({
  providedIn: 'root'
})
export class CombineService {



  companyService = inject(CompanyService);
  employeeService = inject(EmployeesService);

 employees$ = this.employeeService.employees$;
 companies$ = this.companyService.getCompanies();

  constructor() {
  }

  employeesAndCompanies$ =  combineLatest([this.employees$,
    this.companies$]).pipe(
      map(([employees,companies]) =>({
        employees,
        companies
      }))
    );
}
