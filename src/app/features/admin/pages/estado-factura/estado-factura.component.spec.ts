import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoFacturaComponent } from './estado-factura.component';

describe('EstadoFacturaComponent', () => {
  let component: EstadoFacturaComponent;
  let fixture: ComponentFixture<EstadoFacturaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoFacturaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoFacturaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
