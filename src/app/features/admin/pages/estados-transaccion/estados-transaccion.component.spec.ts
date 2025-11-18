import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadosTransaccionComponent } from './estados-transaccion.component';

describe('EstadosTransaccionComponent', () => {
  let component: EstadosTransaccionComponent;
  let fixture: ComponentFixture<EstadosTransaccionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadosTransaccionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadosTransaccionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
