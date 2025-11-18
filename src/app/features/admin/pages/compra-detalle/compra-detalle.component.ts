import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  CompraDetalleService,
  DetalleCompra,
} from '../../../../shared/services/compra-detalle.service';

@Component({
  selector: 'app-compra-detalle',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compra-detalle.component.html',
  styleUrls: ['./compra-detalle.component.scss'],
})
export class CompraDetalleComponent implements OnInit {
  form!: FormGroup;
  detalles: DetalleCompra[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private compraDetalleService: CompraDetalleService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadDetalles();
  }

  private buildForm(): void {
  this.form = this.fb.group({
    idOrdenCompra: [null, [Validators.required, Validators.min(1)]],
    idProducto: [null, [Validators.required, Validators.min(1)]],
    cantidad: [
      1,
      [
        Validators.required,
        Validators.min(1),
      ],
    ],
    precioUnitario: [
      0,
      [
        Validators.required,
        Validators.min(0.01), 
      ],
    ],
  });
}


  loadDetalles(): void {
    this.loading = true;
    this.error = null;

    this.compraDetalleService.getAll().subscribe({
      next: (data) => {
        this.detalles = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar detalles de compra';
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
      this.updateDetalle(this.editingId, body);
    } else {
      this.createDetalle(body);
    }
  }

  private createDetalle(body: any): void {
    this.loading = true;
    this.error = null;

    this.compraDetalleService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadDetalles();
      },
      error: () => {
        this.error = 'Error al crear detalle de compra';
        this.loading = false;
      },
    });
  }

  private updateDetalle(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.compraDetalleService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadDetalles();
      },
      error: () => {
        this.error = 'Error al actualizar detalle de compra';
        this.loading = false;
      },
    });
  }

  editDetalle(d: DetalleCompra): void {
    this.editMode = true;
    this.editingId = d.idDetalleOrdenCompra;

    this.form.patchValue({
      idOrdenCompra: d.idOrdenCompra,
      idProducto: d.idProducto,
      cantidad: d.cantidad,
      precioUnitario: d.precioUnitario,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.resetForm();
  }

  private resetForm(): void {
    this.form.reset({
      idOrdenCompra: null,
      idProducto: null,
      cantidad: 1,
      precioUnitario: 0,
    });
  }

  deleteDetalle(d: DetalleCompra): void {
    if (!confirm(`¿Eliminar detalle de la orden ${d.numeroOrden}?`)) return;

    this.loading = true;
    this.error = null;

    this.compraDetalleService.delete(d.idDetalleOrdenCompra).subscribe({
      next: () => {
        this.loading = false;
        this.loadDetalles();
      },
      error: () => {
        this.error = 'Error al eliminar detalle';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: DetalleCompra): void {
      const accion = row.estado ? 'desactivar' : 'activar';
    
      if (!confirm(`¿Está seguro de ${accion} el estado "${row.idDetalleOrdenCompra}"?`)) {
        return;
      }
    
      this.loading = true;
      this.error = null;
    
      this.compraDetalleService.toggleEstado(row.idDetalleOrdenCompra).subscribe({
        next: () => {
          this.loading = false;
          this.loadDetalles();
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = `No se pudo ${accion} el detalle orden compra`;
        }
      });
    }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
