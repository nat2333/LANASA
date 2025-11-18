import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompraPagosComponent } from './compra-pagos.component';

describe('CompraPagosComponent', () => {
  let component: CompraPagosComponent;
  let fixture: ComponentFixture<CompraPagosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompraPagosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompraPagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
