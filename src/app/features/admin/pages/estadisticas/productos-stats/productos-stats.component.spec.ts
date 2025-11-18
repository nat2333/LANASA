import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosStatsComponent } from './productos-stats.component';

describe('ProductosStatsComponent', () => {
  let component: ProductosStatsComponent;
  let fixture: ComponentFixture<ProductosStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
