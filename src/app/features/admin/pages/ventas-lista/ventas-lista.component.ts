import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import {
  VentasService,
  Venta
} from '../../../../shared/services/ventas.service';

import {
  EstadoFacturaService,
  EstadoFactura
} from '../../../../shared/services/estado-factura.service'; // 👈 servicio para estado_factura

@Component({
  selector: 'app-ventas-lista',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './ventas-lista.component.html',
  styleUrls: ['./ventas-lista.component.scss']
})
export class VentasListaComponent implements OnInit {

  form!: FormGroup;
  ventas: Venta[] = [];

  estadosFactura: EstadoFactura[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private ventasService: VentasService,
    private estadoFacturaService: EstadoFacturaService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadEstadosFactura();
    this.loadVentas();
  }

  private initForm(): void {
    this.form = this.fb.group({
      idCliente: [null, [Validators.required, Validators.min(1)]],
      idProyecto: [null],
      idEstadoFactura: [null, [Validators.required, Validators.min(1)]],
      fechaFacturaVenta: ['', [Validators.required]],
      subtotal: [0, [Validators.required, Validators.min(0)]],
      impuestos: [0, [Validators.required, Validators.min(0)]]
    });
  }

  private loadEstadosFactura(): void {
    this.estadoFacturaService.getAll().subscribe({
      next: data => {
        this.estadosFactura = data ?? [];
      },
      error: err => {
        console.error('[Ventas] error estados factura:', err);
      }
    });
  }

  loadVentas(): void {
    this.loading = true;
    this.error = null;

    this.ventasService.getAll().subscribe({
      next: (data) => {
        this.ventas = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar ventas';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submit();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.getRawValue(); // 👈 por si hay campos deshabilitados

    if (this.editMode && this.editingId !== null) {
      this.updateVenta(this.editingId, body);
    } else {
      this.createVenta(body);
    }
  }

  private createVenta(body: any): void {
    this.loading = true;
    this.error = null;

    // Si en tu DTO de crear te piden total, podrías hacer:
    // body.total = Number(body.subtotal) + Number(body.impuestos);

    this.ventasService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadVentas();
      },
      error: () => {
        this.error = 'Error al crear venta';
        this.loading = false;
      }
    });
  }

  private updateVenta(id: number, body: any): void {
    // En tu back, ActualizarFacturaVentaRequest solo tiene idEstadoFactura
    const payload = {
      idEstadoFactura: body.idEstadoFactura
    };

    this.loading = true;
    this.error = null;

    this.ventasService.update(id, payload).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadVentas();
      },
      error: () => {
        this.error = 'Error al actualizar venta';
        this.loading = false;
      }
    });
  }

  editVenta(row: Venta): void {
    this.editMode = true;
    this.editingId = row.idFacturaVenta;

    // Llenamos el form
    this.form.reset({
      idCliente: row.idCliente,
      idProyecto: row.idProyecto ?? null,
      idEstadoFactura: row.idEstadoFactura ?? null,
      // Ajusta si tu backend no devuelve exactamente este formato
      fechaFacturaVenta: row.fechaFacturaVenta,
      subtotal: row.subtotal,
      impuestos: row.impuestos
    });

    // Solo queremos permitir cambiar el estado en edición:
    this.form.get('idCliente')?.disable({ emitEvent: false });
    this.form.get('idProyecto')?.disable({ emitEvent: false });
    this.form.get('fechaFacturaVenta')?.disable({ emitEvent: false });
    this.form.get('subtotal')?.disable({ emitEvent: false });
    this.form.get('impuestos')?.disable({ emitEvent: false });
    // idEstadoFactura queda habilitado

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteVenta(row: Venta): void {
    if (!confirm(`¿Eliminar venta #${row.numero}?`)) return;

    this.loading = true;
    this.error = null;

    this.ventasService.delete(row.idFacturaVenta).subscribe({
      next: () => {
        this.loading = false;
        this.loadVentas();
      },
      error: () => {
        this.error = 'Error al eliminar venta';
        this.loading = false;
      }
    });
  }

  toggleEstado(row: Venta): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idFacturaVenta}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.ventasService.toggleEstado(row.idFacturaVenta).subscribe({
      next: () => {
        this.loading = false;
        this.loadVentas();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} la venta`;
      }
    });
  }

  resetForm(): void {
    this.form.enable({ emitEvent: false }); // por si venimos de edición
    this.form.reset({
      idCliente: null,
      idProyecto: null,
      idEstadoFactura: null,
      fechaFacturaVenta: '',
      subtotal: 0,
      impuestos: 0
    });
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
