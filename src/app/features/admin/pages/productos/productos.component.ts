import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  ValidatorFn,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { ProductoService, Producto } from '../../../../shared/services/producto.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.scss'],
})
export class ProductosComponent implements OnInit {
  form!: FormGroup;
  productos: Producto[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadProductos();
  }

  private buildForm(): void {
  this.form = this.fb.group(
    {
      sku: ['', [Validators.required, Validators.maxLength(60)]],
      nombre: ['', [Validators.required, Validators.maxLength(150)]],
      descripcion: [''],
      categoria: ['', [Validators.maxLength(80)]],
      precioCompra: [0, [Validators.required, Validators.min(0)]],
      precioVentaSugerido: [0, [Validators.min(0)]],
      stockMinimo: [0, [Validators.min(0)]],
      stockActual: [0, [Validators.min(0)]],
      stockMaximo: [0, [Validators.min(0)]],
    },
    {
      validators: [
        this.validarStocks(),
        this.validarPrecioVentaMayorCompra(),
      ],
    }
  );
}


/** stockMaximo >= stockMinimo y stockActual entre 0 y stockMaximo */
private validarStocks(): import('@angular/forms').ValidatorFn {
  return (group) => {
    const min = group.get('stockMinimo')?.value ?? 0;
    const max = group.get('stockMaximo')?.value ?? 0;
    const actual = group.get('stockActual')?.value ?? 0;

    const errors: any = {};

    if (max && min && max < min) {
      errors.stockMaximoMenor = true;
    }
    if (max && actual && actual > max) {
      errors.stockActualMayor = true;
    }

    return Object.keys(errors).length ? errors : null;
  };
}

private validarPrecioVentaMayorCompra(): import('@angular/forms').ValidatorFn {
  return (group) => {
    const compra = group.get('precioCompra')?.value ?? 0;
    const venta = group.get('precioVentaSugerido')?.value;

    if (venta != null && venta !== '' && venta < compra) {
      return { precioVentaMenorCompra: true };
    }
    return null;
  };
}

  loadProductos(): void {
    this.loading = true;
    this.error = null;

    this.productoService.getAll().subscribe({
      next: (data) => {
        this.productos = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar productos';
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.value;

    if (this.editMode && this.editingId !== null) {
      this.updateProducto(this.editingId, body);
    } else {
      this.createProducto(body);
    }
  }

  private createProducto(body: any): void {
    this.loading = true;
    this.error = null;

    this.productoService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadProductos();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al crear producto';
        this.loading = false;
      },
    });
  }

  private updateProducto(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.productoService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadProductos();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar producto';
        this.loading = false;
      },
    });
  }

  editProducto(p: Producto): void {
    this.editMode = true;
    this.editingId = p.idProducto;

    this.form.patchValue({
      sku: p.sku,
      nombre: p.nombre,
      descripcion: p.descripcion,
      categoria: p.categoria,
      precioCompra: p.precioCompra,
      precioVentaSugerido: p.precioVentaSugerido,
      stockMinimo: p.stockMinimo,
      stockActual: p.stockActual,
      stockMaximo: p.stockMaximo,
    });

     this.form.get('sku')?.disable({ emitEvent: false });
  this.form.get('categoria')?.disable({ emitEvent: false });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.resetForm();
  }

  private resetForm(): void {
    this.form.reset({
      sku: '',
      nombre: '',
      descripcion: '',
      categoria: '',
      precioCompra: 0,
      precioVentaSugerido: 0,
      stockMinimo: 0,
      stockActual: 0,
      stockMaximo: 0,
    });
  }

  deleteProducto(p: Producto): void {
    if (!confirm(`¿Eliminar el producto "${p.nombre}"?`)) return;

    this.loading = true;
    this.error = null;

    this.productoService.delete(p.idProducto).subscribe({
      next: () => {
        this.loading = false;
        this.loadProductos();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al eliminar producto';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: Producto): void {
      const accion = row.estado ? 'desactivar' : 'activar';
    
      if (!confirm(`¿Está seguro de ${accion} el estado "${row.idProducto}"?`)) {
        return;
      }
    
      this.loading = true;
      this.error = null;
    
      this.productoService.toggleEstado(row.idProducto).subscribe({
        next: () => {
          this.loading = false;
          this.loadProductos();
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = `No se pudo ${accion} el metodo pago`;
        }
      });
    }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
