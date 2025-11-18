import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProyectosStatsComponent } from './proyectos-stats.component';

describe('ProyectosStatsComponent', () => {
  let component: ProyectosStatsComponent;
  let fixture: ComponentFixture<ProyectosStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProyectosStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProyectosStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
