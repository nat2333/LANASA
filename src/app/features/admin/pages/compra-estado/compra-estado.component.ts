import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  CompraEstadoService,
  EstadoCompra,
} from '../../../../shared/services/compra-estado.service';

@Component({
  selector: 'app-compra-estado',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './compra-estado.component.html',
  styleUrls: ['./compra-estado.component.scss'],
})
export class CompraEstadoComponent implements OnInit {
  form!: FormGroup;
  estados: EstadoCompra[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private compraEstadoService: CompraEstadoService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadEstados();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      estadoCompra: [
        '',
        [
          Validators.required,
          Validators.maxLength(50),
          // letras, números, espacios y guiones
          Validators.pattern(/^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ0-9\s\-]+$/),
        ],
      ],
    });
  }

  loadEstados(): void {
    this.loading = true;
    this.error = null;

    this.compraEstadoService.getAll().subscribe({
      next: (data) => {
        this.estados = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar estados de compra';
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

    const body = this.form.value;
    this.error = null;

    if (this.editMode && this.editingId !== null) {
      this.update(this.editingId, body);
    } else {
      this.create(body);
    }
  }

  private create(body: any): void {
    this.loading = true;

    this.compraEstadoService.create(body).subscribe({
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

    this.compraEstadoService.update(id, body).subscribe({
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

  editEstado(e: EstadoCompra): void {
    this.editMode = true;
    this.editingId = e.id;

    this.form.patchValue({
      estadoCompra: e.estadoCompra,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteEstado(e: EstadoCompra): void {
    if (!confirm(`¿Eliminar estado "${e.estadoCompra}"?`)) return;

    this.loading = true;
    this.error = null;

    this.compraEstadoService.delete(e.id).subscribe({
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

  toggleEstado(row: EstadoCompra): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado "${row.estadoCompra}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.compraEstadoService.toggleEstado(row.id).subscribe({
      next: () => {
        this.loading = false;
        this.loadEstados();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el estado de compra`;
      },
    });
  }

  reset(): void {
    this.form.reset({ estadoCompra: '' });
    this.form.markAsPristine();
    this.form.markAsUntouched();
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
