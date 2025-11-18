import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompraDetalleComponent } from './compra-detalle.component';

describe('CompraDetalleComponent', () => {
  let component: CompraDetalleComponent;
  let fixture: ComponentFixture<CompraDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraDetalleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompraDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
