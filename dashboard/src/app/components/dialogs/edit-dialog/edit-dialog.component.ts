import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { Company } from '../../../store/companies/company.model';
@Component({
  selector: 'app-edit-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {

  form: FormGroup;
  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<EditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Company
  ) {
    this.form = this.fb.group({
    companyName: [{ value: data.companyName, disabled: true }], // Required, but not editable
    kraPin: [{value: data.kraPin, disabled:true}],
    city: [data.address.city],
    building: [data.address.building],
    street: [data.address.street],
    contactPerson: [data.contactPerson],
    count: [data.count]
});
  }

  onSave() {
    const updated = {
      ...this.data,
      ...this.form.getRawValue() // Gets all form values including disabled ones
    };
    this.dialogRef.close(updated);
  }

  onCancel(){
    this.dialogRef.close();
  }

}
