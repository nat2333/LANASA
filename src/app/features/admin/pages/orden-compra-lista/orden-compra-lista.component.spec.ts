import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdenCompraListaComponent } from './orden-compra-lista.component';

describe('OrdenCompraListaComponent', () => {
  let component: OrdenCompraListaComponent;
  let fixture: ComponentFixture<OrdenCompraListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdenCompraListaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdenCompraListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
