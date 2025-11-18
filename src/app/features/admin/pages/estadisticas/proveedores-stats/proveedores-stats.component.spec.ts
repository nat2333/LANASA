import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedoresStatsComponent } from './proveedores-stats.component';

describe('ProveedoresStatsComponent', () => {
  let component: ProveedoresStatsComponent;
  let fixture: ComponentFixture<ProveedoresStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedoresStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProveedoresStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
