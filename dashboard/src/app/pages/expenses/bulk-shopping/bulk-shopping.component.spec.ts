import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkShoppingComponent } from './bulk-shopping.component';

describe('BulkShoppingComponent', () => {
  let component: BulkShoppingComponent;
  let fixture: ComponentFixture<BulkShoppingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BulkShoppingComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BulkShoppingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
