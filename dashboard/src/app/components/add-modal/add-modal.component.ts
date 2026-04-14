import { Component} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-modal.component.html',
  styleUrl: './add-modal.component.css',
})
export class AddModalComponent {


  constructor(public dialogRef: MatDialogRef<AddModalComponent>){}

  companyResponse = {
    companyName: '',
    kraPin: '',
    contactPerson: '',
    count: '',
    address: {
      street: '',
      city: '',
      building: ''
    },
    phoneNumber: ''
  };

  onSave() {
    this.dialogRef.close(this.companyResponse);
  }

  onClose() {
    this.dialogRef.close();
  }
}
