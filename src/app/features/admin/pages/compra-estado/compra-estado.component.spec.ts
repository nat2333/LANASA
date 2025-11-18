import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompraEstadoComponent } from './compra-estado.component';

describe('CompraEstadoComponent', () => {
  let component: CompraEstadoComponent;
  let fixture: ComponentFixture<CompraEstadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraEstadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompraEstadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
