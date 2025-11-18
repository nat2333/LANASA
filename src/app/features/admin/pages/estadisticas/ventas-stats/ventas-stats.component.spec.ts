import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VentasStatsComponent } from './ventas-stats.component';

describe('VentasStatsComponent', () => {
  let component: VentasStatsComponent;
  let fixture: ComponentFixture<VentasStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VentasStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VentasStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
