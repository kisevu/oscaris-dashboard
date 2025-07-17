import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { EmployeesService } from '../../services/employees/employees.service';
import { Employee } from '../../store/employees/employee.model';
import { Subject, takeUntil, catchError, of } from 'rxjs';



@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css',
})
export class EmployeeComponent implements OnInit,OnDestroy {

  employeeService = inject(EmployeesService);
  employees: Employee[] = [];

  destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees() {
    this.employeeService.getEmployees()
    .pipe(
      catchError(error => {
        console.log(error);
        return of([]);
      }),
      takeUntil(this.destroy$))
      .subscribe( result => this.employees =result);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /*
  - In an event that we do service.subscribe() which means that we are manually subscribing.
  - Then we ought to use takeUntil()  which helps unsubscribe reactively.
  - We equally have the takeUntilDestroy() operator which does not require us to manually call
  ngOnDestroy()  and also we're notified when the Observable is completed through the Completion handler.
  i.e
  import { takeUntilDestroy } from '@angular/core/rxjs-interop';

  this.service.getEmployees().pipe( takeUntilDestroy()).subscribe(result => this.employees = result);
  */


}
