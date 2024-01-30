import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClocksComponent } from './clocks.component';

describe('ClocksComponent', () => {
  let component: ClocksComponent;
  let fixture: ComponentFixture<ClocksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClocksComponent]
    });
    fixture = TestBed.createComponent(ClocksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
