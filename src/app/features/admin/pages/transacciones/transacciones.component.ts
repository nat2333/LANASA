import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  TransaccionService,
  Transaccion
} from '../../../../shared/services/transaccion.service';

import {
  MetodoPagoService,
  MetodoPago
} from '../../../../shared/services/metodo-pago.service';

import {
  EstadoTransaccionService,
  EstadoTransaccion
} from '../../../../shared/services/estado-transaccion.service';

@Component({
  selector: 'app-transacciones',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transacciones.component.html',
  styleUrls: ['./transacciones.component.scss'],
})
export class TransaccionesComponent implements OnInit {

  form!: FormGroup;
  transacciones: Transaccion[] = [];

  metodosPago: MetodoPago[] = [];
  estadosTransaccion: EstadoTransaccion[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private transaccionService: TransaccionService,
    private metodoPagoService: MetodoPagoService,
    private estadoTransaccionService: EstadoTransaccionService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadMetodosPago();
    this.loadEstadosTransaccion();
    this.loadTransacciones();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      idFacturaVenta: [null, [Validators.required, Validators.min(1)]],
      idMetodoPago: [null, [Validators.required, Validators.min(1)]],
      idEstadoTransaccion: [null, [Validators.required, Validators.min(1)]],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      fechaHora: [''] // opcional, el back tiene default CURRENT_TIMESTAMP
    });
  }

  private loadMetodosPago(): void {
    this.metodoPagoService.getAll().subscribe({
      next: data => {
        this.metodosPago = data ?? [];
      },
      error: err => {
        console.error('[Transacciones] error al cargar métodos de pago', err);
      }
    });
  }

  private loadEstadosTransaccion(): void {
    this.estadoTransaccionService.getAll().subscribe({
      next: data => {
        this.estadosTransaccion = data ?? [];
      },
      error: err => {
        console.error('[Transacciones] error al cargar estados transacción', err);
      }
    });
  }

  loadTransacciones(): void {
    this.loading = true;
    this.error = null;

    this.transaccionService.getAll().subscribe({
      next: (data) => {
        this.transacciones = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar transacciones';
        this.loading = false;
      }
    });
  }

  // llamado desde el template (ngSubmit)
  onSubmit(): void {
    this.submit();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue(); // por si hay campos deshabilitados en edición
    const body = {
      ...raw,
      valor: Number(raw.valor),
      fechaHora: raw.fechaHora || null
    };

    if (this.editMode && this.editingId !== null) {
      this.updateTransaccion(this.editingId, body);
    } else {
      this.createTransaccion(body);
    }
  }

  private createTransaccion(body: any): void {
    this.loading = true;
    this.error = null;

    this.transaccionService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadTransacciones();
      },
      error: () => {
        this.error = 'Error al crear transacción';
        this.loading = false;
      }
    });
  }

  private updateTransaccion(id: number, body: any): void {
    // DTO de actualización:
    // idMetodoPago, idEstadoTransaccion, valor, fechaHora, estado
    const payload = {
      idMetodoPago: body.idMetodoPago,
      idEstadoTransaccion: body.idEstadoTransaccion,
      valor: body.valor,
      fechaHora: body.fechaHora || null
    };

    this.loading = true;
    this.error = null;

    this.transaccionService.update(id, payload).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadTransacciones();
      },
      error: () => {
        this.error = 'Error al actualizar transacción';
        this.loading = false;
      }
    });
  }

  // llamado desde el template: (click)="editTransaccion(t)"
  editTransaccion(t: Transaccion): void {
    this.editMode = true;
    this.editingId = t.idTransaccion;

    this.form.reset({
      idFacturaVenta: t.idFacturaVenta,
      idMetodoPago: t.idMetodoPago,
      idEstadoTransaccion: t.idEstadoTransaccion,
      valor: t.valor,
      fechaHora: t.fechaHora // si viene en ISO, el input datetime-local lo muestra bien si es compatible
    });

    // En edición NO dejamos cambiar la factura
    this.form.get('idFacturaVenta')?.disable({ emitEvent: false });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  // llamado desde el template: (click)="deleteTransaccion(t)"
  deleteTransaccion(t: Transaccion): void {
    if (!confirm(`¿Eliminar transacción #${t.idTransaccion}?`)) return;

    this.loading = true;
    this.error = null;

    this.transaccionService.delete(t.idTransaccion).subscribe({
      next: () => {
        this.loading = false;
        this.loadTransacciones();
      },
      error: () => {
        this.error = 'Error al eliminar transacción';
        this.loading = false;
      }
    });
  }

  toggleEstado(row: Transaccion): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idTransaccion}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.transaccionService.toggleEstado(row.idTransaccion).subscribe({
      next: () => {
        this.loading = false;
        this.loadTransacciones();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} la transacción`;
      }
    });
  }

  resetForm(): void {
    this.form.enable({ emitEvent: false }); // por si venimos con idFacturaVenta deshabilitado

    this.form.reset({
      idFacturaVenta: null,
      idMetodoPago: null,
      idEstadoTransaccion: null,
      valor: 0,
      fechaHora: ''
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
