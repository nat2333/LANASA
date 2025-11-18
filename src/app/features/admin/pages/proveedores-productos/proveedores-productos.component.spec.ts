import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedoresProductosComponent } from './proveedores-productos.component';

describe('ProveedoresProductosComponent', () => {
  let component: ProveedoresProductosComponent;
  let fixture: ComponentFixture<ProveedoresProductosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedoresProductosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProveedoresProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
