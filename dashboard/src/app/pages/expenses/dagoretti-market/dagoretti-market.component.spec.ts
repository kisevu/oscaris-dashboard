import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DagorettiMarketComponent } from './dagoretti-market.component';

describe('DagorettiMarketComponent', () => {
  let component: DagorettiMarketComponent;
  let fixture: ComponentFixture<DagorettiMarketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DagorettiMarketComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DagorettiMarketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
