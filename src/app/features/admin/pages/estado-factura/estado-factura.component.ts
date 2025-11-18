import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  EstadoFacturaService,
  EstadoFactura,
} from '../../../../shared/services/estado-factura.service';

@Component({
  selector: 'app-estado-factura',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './estado-factura.component.html',
  styleUrls: ['./estado-factura.component.scss'],
})
export class EstadoFacturaComponent implements OnInit {
  form!: FormGroup;
  estados: EstadoFactura[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private estadoFacturaService: EstadoFacturaService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(40)]],
    });

    this.loadEstados();
  }

  loadEstados(): void {
    this.loading = true;
    this.error = null;

    this.estadoFacturaService.getAll().subscribe({
      next: (data) => {
        this.estados = data ?? [];
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar estados de factura';
        this.loading = false;
      },
    });
  }

  // El HTML usa onSubmit()
  onSubmit(): void {
    this.submit();
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.value;

    if (this.editMode && this.editingId !== null) {
      this.update(this.editingId, body);
    } else {
      this.create(body);
    }
  }

  private create(body: any): void {
    this.loading = true;
    this.error = null;

    this.estadoFacturaService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.reset();
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al crear estado de factura';
        this.loading = false;
      },
    });
  }

  private update(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.estadoFacturaService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al actualizar estado de factura';
        this.loading = false;
      },
    });
  }

  // el HTML usa editEstado(e)
  editEstado(row: EstadoFactura): void {
    this.editMode = true;
    this.editingId = row.idEstadoFactura;

    this.form.patchValue({
      nombre: row.nombre,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteEstado(row: EstadoFactura): void {
    if (!confirm(`¿Eliminar estado de factura "${row.nombre}"?`)) return;

    this.loading = true;
    this.error = null;

    this.estadoFacturaService.delete(row.idEstadoFactura).subscribe({
      next: () => {
        this.loading = false;
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al eliminar estado de factura';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: EstadoFactura): void {
    const accion = row.estado ? 'desactivar' : 'activar';
  
    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idEstadoFactura}"?`)) {
      return;
    }
  
    this.loading = true;
    this.error = null;
  
    this.estadoFacturaService.toggleEstado(row.idEstadoFactura).subscribe({
      next: () => {
        this.loading = false;
        this.loadEstados();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el estado de factura`;
      }
    });
  }

  reset(): void {
    this.form.reset({ nombre: '' });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.reset();
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
