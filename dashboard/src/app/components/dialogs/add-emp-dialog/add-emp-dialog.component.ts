import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { EmployeesService } from '../../../services/employees/employees.service';
import { Observable, of } from 'rxjs';
import { Employee } from '../../../store/employees/employee.model';

@Component({
  selector: 'app-add-emp-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './add-emp-dialog.component.html',
  styleUrl: './add-emp-dialog.component.css'
})
export class AddEmpDialogComponent {

  firstName: FormControl;
  secondName: FormControl;
  ratePerDay: FormControl;
  identificationNo: FormControl;
  residentialArea: FormControl;
  daysPerMonth: FormControl;
  employeeForm: FormGroup;

  constructor(private readonly  formBuilder: FormBuilder, 
    private readonly employeeService: EmployeesService){
    this.firstName = new FormControl('',Validators.required);
    this.secondName = new FormControl('',Validators.required);
    this.ratePerDay = new FormControl(0,Validators.required);
    this.identificationNo = new FormControl('',Validators.required);
    this.residentialArea = new FormControl('',Validators.required);
    this.daysPerMonth = new FormControl(0,Validators.required);

    this.employeeForm = formBuilder.group({
      'firstName': this.firstName,
      'secondName': this.secondName,
      'ratePerDay': this.ratePerDay,
      'identificationNo': this.identificationNo,
      'residentialArea': this.residentialArea,
      'daysPerMonth': this.daysPerMonth
    });
  }


  onSubmit(): void {
    const payload = this.employeeForm.value;
    this.employeeService.addEmployee(payload).subscribe({
      next: res => console.log('Employee added:', res),
      error: err => console.error('Error adding employee:', err)
    });
  }
  

}
