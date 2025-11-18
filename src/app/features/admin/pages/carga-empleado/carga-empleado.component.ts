import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';

interface Cargo {
  id: number;
  nombreCargo: string;
  salario: number;
  estado: boolean;
}

@Component({
  selector: 'app-cargo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './carga-empleado.component.html',
  styleUrls: ['./carga-empleado.component.scss']
})
export class CargoComponent implements OnInit {

  form!: FormGroup;
  cargos: Cargo[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  private base = `${environment.apiUrl}/cargo`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      nombreCargo: ['', [Validators.required, Validators.maxLength(100)]],
      salario: [null, [Validators.required, Validators.min(0)]]
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.http.get<Cargo[]>(this.base).subscribe({
      next: data => {
        this.cargos = data || [];
        this.loading = false;
        this.error = null;
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudieron cargar los cargos';
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;

    // Crear
    if (!this.editMode) {
      const body = {
        nombreCargo: v.nombreCargo,
        salario: v.salario
      };

      this.loading = true;
      this.http.post(this.base, body).subscribe({
        next: () => {
          this.loading = false;
          this.cancel();
          this.load();
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = 'No se pudo crear el cargo';
        }
      });
      return;
    }

    // Editar (DTO de actualización solo tiene salario)
    if (this.editMode && this.editingId != null) {
      const body = {
        salario: v.salario
      };

      this.loading = true;
      this.http.put(`${this.base}/${this.editingId}`, body).subscribe({
        next: () => {
          this.loading = false;
          this.cancel();
          this.load();
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = 'No se pudo actualizar el cargo';
        }
      });
    }
  }

  edit(row: Cargo): void {
    this.editMode = true;
    this.editingId = row.id;

    this.form.setValue({
      id: row.id,
      nombreCargo: row.nombreCargo,
      salario: row.salario
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.editingId = null;
    this.form.reset({
      id: null,
      nombreCargo: '',
      salario: null
    });
  }

  deactivate(row: Cargo): void {
    if (!confirm(`¿Desactivar cargo "${row.nombreCargo}"?`)) return;

    this.loading = true;
    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo desactivar el cargo';
      }
    });
  }
}
