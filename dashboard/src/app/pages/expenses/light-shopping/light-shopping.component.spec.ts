import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LightShoppingComponent } from './light-shopping.component';

describe('LightShoppingComponent', () => {
  let component: LightShoppingComponent;
  let fixture: ComponentFixture<LightShoppingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LightShoppingComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LightShoppingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
