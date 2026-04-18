import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, ReplaySubject, share, switchMap, timer } from 'rxjs';
import { Employee } from '../../store/employees/employee.model';
import { environment } from '../../environments/environment.development';


@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  private readonly apiUrl: string = environment.employeesBaseUrl;

  employees$!: Observable<Employee []>;
  http = inject(HttpClient);

  constructor() {
    // this.employees$ = timer(0,this.interval).pipe(
    //   switchMap(val => this.getEmployees()),
    //   share({connector: () => new ReplaySubject(),
    //     resetOnComplete: true,
    //     resetOnError: true,
    //     resetOnRefCountZero: true
    //   })
    // );
  }


  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee []>(`${this.apiUrl}/all`);
  }

  addEmployee(employeeRequest: Employee): Observable<Employee>{
    return this.http.post<Employee>(`${this.apiUrl}/add-employee`,employeeRequest);
  }

  editEmployee(idNo:string,updatedEmployee: Employee): Observable<Employee>{
    return this.http.patch<Employee>(`${this.apiUrl}/update/${idNo}`,updatedEmployee);
  }

  remove(idNo:string){
    const params =  new HttpParams().set('identificationNo',idNo)
    return this.http.delete<void>(`${this.apiUrl}/delete`,{params});
  }


}
