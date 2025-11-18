import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';

interface Departamento {
  id: number;
  nombre: string;
  codigo: string;
  fechaCreacion: string;       // viene como ISO
  presupuestoAnual: number;
  estado: boolean;
}

@Component({
  selector: 'app-departamentos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './departamentos.component.html',
  styleUrls: ['./departamentos.component.scss'],
})
export class DepartamentosComponent implements OnInit {
  form!: FormGroup;
  departamentos: Departamento[] = [];
  loading = false;
  error: string | null = null;
  editMode = false;

  private base = `${environment.apiUrl}/departamentos`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],                                    // sólo edición
      nombre: ['', Validators.required],             // CrearDepartamentoRequest
      presupuestoAnual: [0, [Validators.required]],  // BigDecimal
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.http.get<Departamento[]>(this.base).subscribe({
      next: data => {
        this.departamentos = data || [];
        this.loading = false;
        this.error = null;
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudieron cargar los departamentos';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;
    let req;

    if (this.editMode && v.id != null) {
      // ActualizarDepartamentoRequest: sólo presupuestoAnual
      const payload = {
        presupuestoAnual: v.presupuestoAnual,
      };
      req = this.http.put(`${this.base}/${v.id}`, payload);
    } else {
      // CrearDepartamentoRequest: nombre, presupuestoAnual
      const payload = {
        nombre: v.nombre,
        presupuestoAnual: v.presupuestoAnual,
      };
      req = this.http.post(this.base, payload);
    }

    this.loading = true;
    req.subscribe({
      next: () => {
        this.loading = false;
        this.cancel();
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'Operación fallida';
      },
    });
  }

  edit(row: Departamento): void {
    this.editMode = true;

    this.form.patchValue({
      id: row.id,
      nombre: row.nombre,
      presupuestoAnual: row.presupuestoAnual,
    });

    // en edición no dejamos cambiar el nombre
    this.form.get('nombre')?.disable();

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      nombre: '',
      presupuestoAnual: 0,
    });
    this.form.get('nombre')?.enable();
  }

  remove(row: Departamento): void {
    if (!confirm(`¿Desactivar/eliminar el departamento "${row.nombre}"?`)) return;

    this.loading = true;
    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo eliminar el departamento';
      },
    });
  }

  toggleEstado(row: Departamento): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el departamento "${row.nombre}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.id}/estado`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el departamento`;
      },
    });
  }
}
