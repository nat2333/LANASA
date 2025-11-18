import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  VentaDetalleService,
  DetalleFacturaVenta,
} from '../../../../shared/services/venta-detalle.service';

@Component({
  selector: 'app-venta-detalle',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './venta-detalle.component.html',
  styleUrls: ['./venta-detalle.component.scss'],
})
export class VentaDetalleComponent implements OnInit {
  form!: FormGroup;
  detalles: DetalleFacturaVenta[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private ventaDetalleService: VentaDetalleService
  ) {}

  ngOnInit(): void {
    this.buildForm();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      idFacturaVenta: [null, [Validators.required, Validators.min(1)]],
      idProducto: [null, [Validators.required, Validators.min(1)]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precioUnitario: [0, [Validators.required, Validators.min(0.01)]],
      tipo: ['', [Validators.maxLength(20)]],
    });
  }

  // 🔍 cargar detalles SOLO por factura
  loadDetallesPorFactura(): void {
    this.loading = true;
    this.error = null;

    const idFacturaVenta = this.form.get('idFacturaVenta')?.value;

    if (!idFacturaVenta || idFacturaVenta <= 0) {
      this.loading = false;
      this.error = 'Debe ingresar un ID de factura válido.';
      return;
    }

    this.ventaDetalleService.getByFactura(idFacturaVenta).subscribe({
      next: (data) => {
        this.detalles = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar detalles de la factura.';
        this.loading = false;
      },
    });
  }

  // el template usa (ngSubmit)="onSubmit()"
  onSubmit(): void {
    this.submit();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.getRawValue();

    if (this.editMode && this.editingId !== null) {
      this.updateDetalle(this.editingId, body);
    } else {
      this.createDetalle(body);
    }
  }

  private createDetalle(body: any): void {
    this.loading = true;
    this.error = null;

    this.ventaDetalleService.create(body).subscribe({
      next: () => {
        this.loading = false;
        // recargamos la misma factura
        this.loadDetallesPorFactura();
        this.resetForm();
      },
      error: () => {
        this.error = 'Error al crear detalle';
        this.loading = false;
      },
    });
  }

  private updateDetalle(id: number, body: any): void {
    // asumiendo que tu DTO de actualización solo espera cantidad y precioUnitario
    const payload = {
      cantidad: body.cantidad,
      precioUnitario: body.precioUnitario,
      // si en tu backend también actualizas tipo, añade: tipo: body.tipo
    };

    this.loading = true;
    this.error = null;

    this.ventaDetalleService.update(id, payload).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadDetallesPorFactura();
      },
      error: () => {
        this.error = 'Error al actualizar detalle';
        this.loading = false;
      },
    });
  }

  editDetalle(d: DetalleFacturaVenta): void {
    this.editMode = true;
    this.editingId = d.idDetalleFacturaVenta;

    this.form.reset({
      idFacturaVenta: d.idFacturaVenta,
      idProducto: d.idProducto,
      cantidad: d.cantidad,
      precioUnitario: d.precioUnitario,
      tipo: d.tipo,
    });

    // en edición no permitimos cambiar factura ni producto
    this.form.get('idFacturaVenta')?.disable({ emitEvent: false });
    this.form.get('idProducto')?.disable({ emitEvent: false });
    // si tipo NO se puede actualizar en backend, también podrías deshabilitarlo:
    // this.form.get('tipo')?.disable({ emitEvent: false });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteDetalle(d: DetalleFacturaVenta): void {
    if (!confirm(`¿Eliminar línea de "${d.nombreProducto}"?`)) return;

    this.loading = true;
    this.error = null;

    this.ventaDetalleService.delete(d.idDetalleFacturaVenta).subscribe({
      next: () => {
        this.loading = false;
        this.loadDetallesPorFactura();
      },
      error: () => {
        this.error = 'Error al eliminar detalle';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: DetalleFacturaVenta): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idDetalleFacturaVenta}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.ventaDetalleService.toggleEstado(row.idDetalleFacturaVenta).subscribe({
      next: () => {
        this.loading = false;
        this.loadDetallesPorFactura();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el detalle venta`;
      }
    });
  }

  resetForm(): void {
    // re-habilitar por si venimos de edición
    this.form.enable({ emitEvent: false });

    const currentFacturaId = this.form.get('idFacturaVenta')?.value ?? null;

    this.form.reset({
      idFacturaVenta: currentFacturaId, // mantenemos la factura actual
      idProducto: null,
      cantidad: 1,
      precioUnitario: 0,
      tipo: '',
    });

    this.editMode = false;
    this.editingId = null;
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.resetForm();
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
