import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpicesRiceComponent } from './spices-rice.component';

describe('SpicesRiceComponent', () => {
  let component: SpicesRiceComponent;
  let fixture: ComponentFixture<SpicesRiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpicesRiceComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SpicesRiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
