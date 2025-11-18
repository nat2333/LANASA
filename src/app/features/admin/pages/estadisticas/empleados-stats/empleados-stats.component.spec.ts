import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadosStatsComponent } from './empleados-stats.component';

describe('EmpleadosStatsComponent', () => {
  let component: EmpleadosStatsComponent;
  let fixture: ComponentFixture<EmpleadosStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpleadosStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmpleadosStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
