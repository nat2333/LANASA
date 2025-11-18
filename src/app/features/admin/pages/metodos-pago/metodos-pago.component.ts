import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder, FormGroup, ReactiveFormsModule, Validators
} from '@angular/forms';

import {
  MetodoPagoService,
  MetodoPago
} from '../../../../shared/services/metodo-pago.service';

@Component({
  selector: 'app-metodos-pago',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './metodos-pago.component.html',
  styleUrls: ['./metodos-pago.component.scss']
})
export class MetodosPagoComponent implements OnInit {

  form!: FormGroup;
  metodos: MetodoPago[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private metodoPagoService: MetodoPagoService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      metodoPago: ['', [Validators.required, Validators.maxLength(50)]]
    });

    this.loadMetodos();
  }

  loadMetodos(): void {
    this.loading = true;
    this.error = null;

    this.metodoPagoService.getAll().subscribe({
      next: (data) => {
        this.metodos = data ?? [];
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar los métodos de pago';
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

    const body = this.form.value; // { metodoPago }

    if (this.editMode && this.editingId !== null) {
      this.update(this.editingId, body);
    } else {
      this.create(body);
    }
  }

  private create(body: any): void {
    this.loading = true;
    this.error = null;

    this.metodoPagoService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.reset();
        this.loadMetodos();
      },
      error: () => {
        this.error = 'Error al crear método';
        this.loading = false;
      }
    });
  }

  private update(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.metodoPagoService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadMetodos();
      },
      error: () => {
        this.error = 'Error al actualizar';
        this.loading = false;
      }
    });
  }

  editMetodo(row: MetodoPago): void {
    this.editMode = true;
    this.editingId = row.id;

    this.form.patchValue({
      metodoPago: row.metodoPago
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteMetodo(row: MetodoPago): void {
    if (!confirm(`¿Eliminar método "${row.metodoPago}"?`)) return;

    this.loading = true;
    this.error = null;

    this.metodoPagoService.delete(row.id).subscribe({
      next: () => {
        this.loading = false;
        this.loadMetodos();
      },
      error: () => {
        this.error = 'Error al eliminar método';
        this.loading = false;
      }
    });
  }

  toggleEstado(row: MetodoPago): void {
    const accion = row.estado ? 'desactivar' : 'activar';
  
    if (!confirm(`¿Está seguro de ${accion} el estado "${row.metodoPago}"?`)) {
      return;
    }
  
    this.loading = true;
    this.error = null;
  
    this.metodoPagoService.toggleEstado(row.id).subscribe({
      next: () => {
        this.loading = false;
        this.loadMetodos();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el método de pago`;
      }
    });
  }

  reset(): void {
    this.form.reset({
      metodoPago: ''
    });
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
