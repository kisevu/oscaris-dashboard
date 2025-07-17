import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeautifulCardsComponent } from './beautiful-cards.component';

describe('BeautifulCardsComponent', () => {
  let component: BeautifulCardsComponent;
  let fixture: ComponentFixture<BeautifulCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeautifulCardsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BeautifulCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
