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
import { forkJoin } from 'rxjs';

interface HistorialEmpleado {
  id: number;
  estado: boolean;
  fechaInicio: string;
  fechaFin?: string | null;
  cedulaEmpleado: string;
  nombreDepartamento: string;
  nombreCargo: string;
}

interface Empleado {
  id: number;
  cedula: string;
  primerNombre: string;
  segundoNombre?: string;
  primerApellido: string;
  segundoApellido?: string;
  estado: boolean;
}

interface Departamento {
  id: number;
  nombre: string;
  estado: boolean;
}

interface Cargo {
  id: number;
  nombreCargo: string;
  estado: boolean;
}

@Component({
  selector: 'app-historico-empleado',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './historico-empleado.component.html',
  styleUrls: ['./historico-empleado.component.scss'],
})
export class HistoricoEmpleadoComponent implements OnInit {
  form!: FormGroup;
  rows: HistorialEmpleado[] = [];

  empleados: Empleado[] = [];
  departamentos: Departamento[] = [];
  cargos: Cargo[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;

  private base = `${environment.apiUrl}/empleado-historico`; // ajusta si tu API usa otro path
  private baseEmpleado = `${environment.apiUrl}/empleados`;
  private baseDepartamento = `${environment.apiUrl}/departamentos`;
  private baseCargo = `${environment.apiUrl}/cargo`;
  
  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      idEmpleado: ['', Validators.required],
      idDepartamento: ['', Validators.required],
      idCargo: ['', Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: [''],// opcional
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    forkJoin({
      historico: this.http.get<HistorialEmpleado[]>(this.base),
      empleados: this.http.get<Empleado[]>(this.baseEmpleado),
      departamentos: this.http.get<Departamento[]>(this.baseDepartamento),
      cargos: this.http.get<Cargo[]>(this.baseCargo)
    }).subscribe({
      next: data => {
        this.rows = data.historico || [];
        this.empleados = data.empleados.filter(e => e.estado) || [];
        this.departamentos = data.departamentos.filter(d => d.estado) || [];
        this.cargos = data.cargos.filter(c => c.estado) || [];
        this.loading = false;
        this.error = null;
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo cargar el historial de empleados';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.getRawValue();
    let req;

    if (this.editMode && v.id != null) {
      // ActualizarHistorialRequest: solo fechaFin
      const payload = {
        fechaFin: v.fechaFin || null,
      };
      req = this.http.put(`${this.base}/${v.id}`, payload);
    } else {
      // CrearHistorialRequest
      const payload = {
        idEmpleado: Number(v.idEmpleado),
        idDepartamento: Number(v.idDepartamento),
        idCargo: Number(v.idCargo),
        fechaInicio: v.fechaInicio,
        fechaFin: v.fechaFin || null,
      };
      req = this.http.post(this.base, payload);
    }

    this.loading = true;
    this.error = null;

    req.subscribe({
      next: () => {
        this.loading = false;
        this.cancel();
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo guardar el registro';
      },
    });
  }

  edit(row: HistorialEmpleado): void {
    this.editMode = true;

    // Solo permitimos editar fechaFin (según tu DTO de actualización)
    this.form.reset({
      id: row.id,
      idEmpleado: null,
      idDepartamento: null,
      idCargo: null,
      fechaInicio: row.fechaInicio?.substring(0, 10),
      fechaFin: row.fechaFin ? row.fechaFin.substring(0, 10) : '',
    });

    this.form.get('idEmpleado')?.disable();
    this.form.get('idDepartamento')?.disable();
    this.form.get('idCargo')?.disable();
    this.form.get('fechaInicio')?.disable();

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      idEmpleado: null,
      idDepartamento: null,
      idCargo: null,
      fechaInicio: '',
      fechaFin: '',
    });
    this.form.get('idEmpleado')?.enable();
    this.form.get('idDepartamento')?.enable();
    this.form.get('idCargo')?.enable();
    this.form.get('fechaInicio')?.enable();
  }

  remove(row: HistorialEmpleado): void {
    if (!confirm(`¿Desactivar/eliminar el registro de "${row.cedulaEmpleado}"?`)) return;

    this.loading = true;
    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo eliminar el registro';
      },
    });
  }

  toggleEstado(row: HistorialEmpleado): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el registro de "${row.cedulaEmpleado}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.id}/cerrar`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el registro`;
      },
    });
  }

  buscarIdEmpleado(cedula: string): number | string {
    const emp = this.empleados.find(e => e.cedula === cedula);
    return emp ? emp.id : '';
  }

  buscarIdDepartamento(nombre: string): number | string {
    const dep = this.departamentos.find(d => d.nombre === nombre);
    return dep ? dep.id : '';
  }

  buscarIdCargo(nombre: string): number | string {
    const cargo = this.cargos.find(c => c.nombreCargo === nombre);
    return cargo ? cargo.id : '';
  }

   getNombreCompleto(emp: Empleado): string {
    return `${emp.cedula} - ${emp.primerNombre} ${emp.segundoNombre || ''} ${emp.primerApellido} ${emp.segundoApellido || ''}`.trim();
  }
}
