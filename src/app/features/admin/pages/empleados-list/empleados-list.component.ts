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

interface Empleado {
  id: number;
  cedula: string;
  primerNombre: string;
  segundoNombre?: string | null;
  primerApellido: string;
  segundoApellido?: string | null;
  correo: string;
  fechaNacimiento: string;
  direccion?: string | null;
  ciudad?: string | null;
  pais?: string | null;
  fechaIngreso?: string | null;
  salario: number;
  estado: boolean;
  idCargo: number;             
  idTipoContrato: number;       
  idDepartamento: number; 
  nombreCargo: string;
  nombreTipoContrato: string;
  nombreDepartamento: string;
}

interface Cargo {
  id: number;
  nombreCargo: string;
  estado: boolean;
}

interface TipoContrato {
  id: number;
  nombreTipocontrato: string;
  estado: boolean;
}

interface Departamento {
  id: number;
  nombre: string;
  estado: boolean;
}

@Component({
  selector: 'app-empleados-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './empleados-list.component.html',
  styleUrls: ['./empleados-list.component.scss'],
})

export class EmpleadosListComponent implements OnInit {
  form!: FormGroup;
  empleados: Empleado[] = [];
  loading = false;
  error: string | null = null;
  editMode = false;

  cargos: Cargo[] = [];
  tiposContrato: TipoContrato[] = [];
  departamentos: Departamento[] = [];

  private base = `${environment.apiUrl}/empleados`;
  private baseCargo = `${environment.apiUrl}/cargo`; 
  private baseTipoContrato = `${environment.apiUrl}/tipo-contrato`;
  private baseDepartamento = `${environment.apiUrl}/departamentos`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],

      // Campos de creación (CrearEmpleadoRequest)
      cedula: ['', [Validators.required, Validators.maxLength(20)]],
      primerNombre: ['', [Validators.required, Validators.maxLength(60)]],
      segundoNombre: [''],
      primerApellido: ['', [Validators.required, Validators.maxLength(60)]],
      segundoApellido: [''],
      correo: ['', [Validators.required, Validators.email]],
      fechaNacimiento: ['', Validators.required],

      direccion: [''],
      ciudad: [''],
      pais: [''],

      salario: [0, [Validators.required, Validators.min(0)]],
      idCargo: ['', Validators.required],
      idTipoContrato: ['', Validators.required],
      idDepartamento: ['', Validators.required],
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    forkJoin({
      empleados: this.http.get<Empleado[]>(this.base),
      cargos: this.http.get<Cargo[]>(this.baseCargo),
      tiposContrato: this.http.get<TipoContrato[]>(this.baseTipoContrato),
      departamentos: this.http.get<Departamento[]>(this.baseDepartamento)
    }).subscribe({
      next: data => {
        this.empleados = data.empleados || [];
        this.cargos = data.cargos.filter(c => c.estado) || [];
        this.tiposContrato = data.tiposContrato.filter(tc => tc.estado) || [];
        this.departamentos = data.departamentos.filter(d => d.estado) || [];
        this.loading = false;
        this.error = null;
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudieron cargar los empleados';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.getRawValue();
    let req;

    if (this.editMode && v.id != null) {
      // ActualizarEmpleadoRequest
      const payload = {
        primerNombre: v.primerNombre,
        segundoNombre: v.segundoNombre || null,
        primerApellido: v.primerApellido,
        segundoApellido: v.segundoApellido || null,
        direccion: v.direccion || null,
        ciudad: v.ciudad || null,
        pais: v.pais || null,
        salario: v.salario,
        idCargo: Number(v.idCargo),          
        idTipoContrato: Number(v.idTipoContrato),  
        idDepartamento: Number(v.idDepartamento),
      };
      req = this.http.put(`${this.base}/${v.id}`, payload);
    } else {
      // CrearEmpleadoRequest
      const payload = {
        cedula: v.cedula,
        primerNombre: v.primerNombre,
        segundoNombre: v.segundoNombre || null,
        primerApellido: v.primerApellido,
        segundoApellido: v.segundoApellido || null,
        correo: v.correo,
        fechaNacimiento: v.fechaNacimiento,
        direccion: v.direccion || null,
        ciudad: v.ciudad || null,
        pais: v.pais || null,
        salario: v.salario,
        idCargo: Number(v.idCargo),          
        idTipoContrato: Number(v.idTipoContrato),  
        idDepartamento: Number(v.idDepartamento),  
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
        this.error = 'No se pudo guardar el empleado';
      },
    });
  }

  edit(row: Empleado): void {
    this.editMode = true;

    this.form.reset({
      id: row.id,
      cedula: row.cedula,
      primerNombre: row.primerNombre,
      segundoNombre: row.segundoNombre || '',
      primerApellido: row.primerApellido,
      segundoApellido: row.segundoApellido || '',
      correo: row.correo,
      fechaNacimiento: row.fechaNacimiento?.substring(0, 10),
      direccion: row.direccion || '',
      ciudad: row.ciudad || '',
      pais: row.pais || '',
      salario: row.salario,
      idCargo: row.idCargo || '',          
      idTipoContrato: row.idTipoContrato || '', 
      idDepartamento: row.idDepartamento || '',
    });

    // En edición no permitimos cambiar cedula/correo/fechaNacimiento
    this.form.get('cedula')?.disable();
    this.form.get('correo')?.disable();
    this.form.get('fechaNacimiento')?.disable();

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      cedula: '',
      primerNombre: '',
      segundoNombre: '',
      primerApellido: '',
      segundoApellido: '',
      correo: '',
      fechaNacimiento: '',
      direccion: '',
      ciudad: '',
      pais: '',
      salario: 0,
      idCargo: '',
      idTipoContrato: '',
      idDepartamento: '',
    });
    this.form.get('cedula')?.enable();
    this.form.get('correo')?.enable();
    this.form.get('fechaNacimiento')?.enable();
  }

  remove(row: Empleado): void {
    if (!confirm(`¿Desactivar/eliminar al empleado "${row.cedula} - ${row.primerNombre}"?`)) return;

    this.loading = true;
    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo eliminar el empleado';
      },
    });
  }

  toggleEstado(row: Empleado): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} al empleado "${row.primerNombre} ${row.primerApellido}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.id}/estado`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el empleado`;
      },
    });
  }
}
