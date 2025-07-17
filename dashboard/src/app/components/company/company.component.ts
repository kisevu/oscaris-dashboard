import { Component, Inject, inject, OnInit } from '@angular/core';
import { CompanyService } from '../../services/company/company.service';
import { AddModalComponent } from '../add-modal/add-modal.component';
import { Store } from '@ngrx/store';
import { selectAllCompanies, selectCompanyLoading } from '../../store/companies/company.selectors';
import { addcompany, deleteCompany, loadCompanies, updateCompany } from '../../store/companies/company.actions';
import { AsyncPipe } from '@angular/common';
import { Company } from '../../store/companies/company.model';
import { MatDialog } from '@angular/material/dialog';
import { EditDialogComponent } from '../dialogs/edit-dialog/edit-dialog.component';


@Component({
  selector: 'app-company',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './company.component.html',
  styleUrl: './company.component.css',
})
export class CompanyComponent implements OnInit {

  companies: Array<any> = [];

  constructor(private readonly store: Store, private readonly dialog:MatDialog){}

  companie$ =this.store.select(selectAllCompanies);
  loading$ = this.store.select(selectCompanyLoading);

  companyService = inject(CompanyService);

  ngOnInit(): void {
    this.store.dispatch(loadCompanies());
  }

  remove(company: Company) {
    if (confirm(`Are you sure you want to delete ${company.companyName}?`)) {
      this.store.dispatch(deleteCompany({ companyName: company.companyName }));
    }
  }


 onCompanySave(newCompany: Company) {
  this.store.dispatch(addcompany({ company: newCompany }));
 }

addComp() {
  const dialogRef = this.dialog.open(AddModalComponent, {
    width: '500px'
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.companyService.addCompany(result).subscribe({
        next: () => {
          this.store.dispatch(loadCompanies()); // Reload list
        },
        error: err => {
          console.error('Failed to add company', err);
        }
      });
    }
  });
}


 edit(company: Company) {
  const dialogRef = this.dialog.open(EditDialogComponent, {
    width: '500px',
    data: company
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      // Call the service directly
      this.companyService.updateCompany(result).subscribe({
        next: () => {
          // Optionally reload the list or show success
          this.store.dispatch(loadCompanies());
        },
        error: err => {
          console.error('Update failed', err);
        }
      });
    }
  });
}

}
