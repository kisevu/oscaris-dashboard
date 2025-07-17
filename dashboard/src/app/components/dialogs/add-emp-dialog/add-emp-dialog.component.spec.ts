import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEmpDialogComponent } from './add-emp-dialog.component';

describe('AddEmpDialogComponent', () => {
  let component: AddEmpDialogComponent;
  let fixture: ComponentFixture<AddEmpDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEmpDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddEmpDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
