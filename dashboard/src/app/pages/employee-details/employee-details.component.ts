import { Component, inject, ChangeDetectionStrategy } from '@angular/core';
import { EmployeesService } from '../../services/employees/employees.service';
import { AsyncPipe } from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { AddEmpDialogComponent } from '../../components/dialogs/add-emp-dialog/add-emp-dialog.component';
import { Employee } from '../../store/employees/employee.model';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-employee-details',
  standalone: true,
  imports: [AsyncPipe,MatButtonModule, MatDialogModule],
  templateUrl: './employee-details.component.html',
  styleUrl: './employee-details.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class EmployeeDetailsComponent {

   readonly dialog = inject(MatDialog);

  employeeService = inject(EmployeesService);

  employees$ =  this.employeeService.employees$;

  openDialog() {
    const dialogRef = this.dialog.open(AddEmpDialogComponent);
    dialogRef.afterClosed().subscribe( result => {
      console.log(`Dialog  result ${result}`);
    })
  }

  remove(idNo:string): void {
    this.employeeService.remove(idNo);
  }

  edit(idNo: string): Observable<Employee>{
    return of<Employee>();
  }

  



}
