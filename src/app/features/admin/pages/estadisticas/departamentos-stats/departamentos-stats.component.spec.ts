import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartamentosStatsComponent } from './departamentos-stats.component';

describe('DepartamentosStatsComponent', () => {
  let component: DepartamentosStatsComponent;
  let fixture: ComponentFixture<DepartamentosStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartamentosStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepartamentosStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
