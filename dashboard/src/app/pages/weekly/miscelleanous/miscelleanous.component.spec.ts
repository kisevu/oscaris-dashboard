import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MiscelleanousComponent } from './miscelleanous.component';

describe('MiscelleanousComponent', () => {
  let component: MiscelleanousComponent;
  let fixture: ComponentFixture<MiscelleanousComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MiscelleanousComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MiscelleanousComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
