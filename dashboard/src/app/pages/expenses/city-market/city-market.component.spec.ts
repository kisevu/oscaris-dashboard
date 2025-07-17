import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CityMarketComponent } from './city-market.component';

describe('CityMarketComponent', () => {
  let component: CityMarketComponent;
  let fixture: ComponentFixture<CityMarketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CityMarketComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CityMarketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
