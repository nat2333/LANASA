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

interface RolEmpleado {
  idRolEmpleado: number;
  rolEmpleado: string;
  tarifaHora: number;
  estado: boolean;
}

@Component({
  selector: 'app-proyecto-rol-empleado',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proyecto-rol-empleado.component.html',
  styleUrls: ['./proyecto-rol-empleado.component.scss'],
})
export class ProyectoRolEmpleadoComponent implements OnInit {
  form!: FormGroup;
  roles: RolEmpleado[] = [];

  loading = false;
  saving = false;
  deleting = false;
  error: string | null = null;
  editMode = false;

  private base = `${environment.apiUrl}/rol-empleado`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      idRolEmpleado: [null],
      rolEmpleado: ['', Validators.required],
      tarifaHora: [0, [Validators.required, Validators.min(0)]],
      estado: [true, Validators.required],   // Boolean
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.http.get<RolEmpleado[]>(this.base).subscribe({
      next: (data) => {
        this.roles = data || [];
        this.loading = false;
      },
      error: (e) => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudo cargar roles de empleado';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;

    const payload = {
      rolEmpleado: v.rolEmpleado,
      tarifaHora:
        v.tarifaHora !== null && v.tarifaHora !== ''
          ? Number(v.tarifaHora)
          : 0,
      // estado no va en Crear / Actualizar del DTO, lo maneja backend.
    };

    let req;
    this.saving = true;
    this.error = null;

    if (this.editMode && v.idRolEmpleado) {
      // ActualizarRolEmpleadoRequest
      req = this.http.put(`${this.base}/${v.idRolEmpleado}`, payload);
    } else {
      // CrearRolEmpleadoRequest
      req = this.http.post(this.base, payload);
    }

    req.subscribe({
      next: () => {
        this.saving = false;
        this.cancel();
        this.load();
      },
      error: (e) => {
        console.error(e);
        this.saving = false;
        this.error = 'No se pudo guardar el rol de empleado';
      },
    });
  }

  edit(row: RolEmpleado): void {
    this.editMode = true;

    this.form.setValue({
      idRolEmpleado: row.idRolEmpleado,
      rolEmpleado: row.rolEmpleado,
      tarifaHora: row.tarifaHora ?? 0,
      estado: row.estado ?? true,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    if (this.form) {
      this.form.reset({
        idRolEmpleado: null,
        rolEmpleado: '',
        tarifaHora: 0,
        estado: true,
      });
    }
  }

  toggleEstado(row: RolEmpleado): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el cliente "${row.idRolEmpleado}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.idRolEmpleado}/estado`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el cliente`;
      },
    });
  }
}
