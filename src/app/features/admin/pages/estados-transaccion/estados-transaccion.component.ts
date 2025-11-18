import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  EstadoTransaccionService,
  EstadoTransaccion,
} from '../../../../shared/services/estado-transaccion.service';

@Component({
  selector: 'app-estados-transaccion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './estados-transaccion.component.html',
  styleUrls: ['./estados-transaccion.component.scss'],
})
export class EstadosTransaccionComponent implements OnInit {
  form!: FormGroup;
  estados: EstadoTransaccion[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private estadoTransaccionService: EstadoTransaccionService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      estadoTransaccion: ['', [Validators.required, Validators.maxLength(40)]],
      estado: [true], // checkbox "Activo"
    });

    this.loadEstados();
  }

  loadEstados(): void {
    this.loading = true;
    this.error = null;

    this.estadoTransaccionService.getAll().subscribe({
      next: (data) => {
        this.estados = data ?? [];
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar estados de transacción';
        this.loading = false;
      },
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

    const body = {
      estadoTransaccion: this.form.value.estadoTransaccion,
      estado: this.form.value.estado,
    };

    if (this.editMode && this.editingId !== null) {
      this.update(this.editingId, body);
    } else {
      this.create(body);
    }
  }

  private create(body: any): void {
    this.loading = true;
    this.error = null;

    this.estadoTransaccionService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.reset();
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al crear estado';
        this.loading = false;
      },
    });
  }

  private update(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.estadoTransaccionService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al actualizar estado';
        this.loading = false;
      },
    });
  }

  editEstado(row: EstadoTransaccion): void {
    this.editMode = true;
    this.editingId = row.id;

    this.form.patchValue({
      estadoTransaccion: row.nombre,
      estado: row.estado,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteEstado(row: EstadoTransaccion): void {
    if (!confirm(`¿Eliminar estado "${row.nombre}"?`)) return;

    this.loading = true;
    this.error = null;

    this.estadoTransaccionService.delete(row.id).subscribe({
      next: () => {
        this.loading = false;
        this.loadEstados();
      },
      error: () => {
        this.error = 'Error al eliminar estado';
        this.loading = false;
      },
    });
  }

  reset(): void {
    this.form.reset({
      estadoTransaccion: '',
      estado: true,
    });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.reset();
  }

  toggleEstado(row: EstadoTransaccion): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.nombre}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.estadoTransaccionService.toggleEstado(row.id).subscribe({
      next: () => {
        this.loading = false;
        this.loadEstados();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el estado de transacción`;
      }
    });
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
