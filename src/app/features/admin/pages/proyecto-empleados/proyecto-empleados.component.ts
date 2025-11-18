import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { environment } from '../../../../../environments/environment';

interface EmpleadoProyecto {
  idEmpleadoProyecto: number;
  idEmpleado: number;
  nombreEmpleado: string;
  idProyecto: number;
  codigoProyecto: string;
  nombreProyecto: string;
  idRol: number;
  rol: string;
  fechaInicio: string;
  fechaFin: string | null;
  horasTrabajadas: number | null;
  estado: boolean;
}

@Component({
  selector: 'app-proyecto-empleados',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proyecto-empleados.component.html',
  styleUrls: ['./proyecto-empleados.component.scss']
})
export class ProyectoEmpleadosComponent implements OnInit {
  form!: FormGroup;
  rows: EmpleadoProyecto[] = [];
  loading = false;
  error: string | null = null;
  editMode = false;
  idProyecto!: number;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private route: ActivatedRoute
  ) {}

  private get base() {
    return `${environment.apiUrl}/empleado-proyecto`;
  }

  ngOnInit(): void {
    this.idProyecto = Number(this.route.snapshot.paramMap.get('id'));

    this.form = this.fb.group({
      idEmpleadoProyecto: [null],
      idEmpleado: [null, Validators.required],
      idRol: [null, Validators.required],
      fechaInicio: ['', Validators.required],
      fechaFin: [''],
      horasTrabajadas: ['']
    });

    this.load();
  }

  load(): void {
    this.loading = true;

    this.http
      .get<EmpleadoProyecto[]>(`${this.base}/proyecto/${this.idProyecto}`)
      .subscribe({
        next: (data) => {
          this.rows = data || [];
          this.loading = false;
          this.error = null;
        },
        error: (err) => {
          console.error(err);
          this.loading = false;
          this.error = 'No se pudo cargar la asignación de empleados';
        }
      });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;

    let req;

    if (this.editMode) {
      // ActualizarEmpleadoProyectoRequest
      const payload = {
        idRol: v.idRol,
        fechaInicio: v.fechaInicio,
        fechaFin: v.fechaFin || null,
        horasTrabajadas: v.horasTrabajadas ? Number(v.horasTrabajadas) : null,
        estado: true
      };

      req = this.http.put(
        `${this.base}/${v.idEmpleadoProyecto}`,
        payload
      );
    } else {
      // CrearEmpleadoProyectoRequest
      const payload = {
        idEmpleado: v.idEmpleado,
        idProyecto: this.idProyecto,
        idRol: v.idRol,
        fechaInicio: v.fechaInicio,
        fechaFin: v.fechaFin || null,
        horasTrabajadas: v.horasTrabajadas ? Number(v.horasTrabajadas) : null
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
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo guardar el registro';
      }
    });
  }

  edit(row: EmpleadoProyecto): void {
    this.editMode = true;

    this.form.setValue({
      idEmpleadoProyecto: row.idEmpleadoProyecto,
      idEmpleado: row.idEmpleado,
      idRol: row.idRol,
      fechaInicio: row.fechaInicio.substring(0, 10),
      fechaFin: row.fechaFin ? row.fechaFin.substring(0, 10) : '',
      horasTrabajadas: row.horasTrabajadas ?? ''
    });

    this.form.get('idEmpleado')?.disable();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      idEmpleadoProyecto: null,
      idEmpleado: null,
      idRol: null,
      fechaInicio: '',
      fechaFin: '',
      horasTrabajadas: ''
    });
    this.form.get('idEmpleado')?.enable();
  }

  remove(row: EmpleadoProyecto): void {
    if (!confirm(`¿Desactivar relación de "${row.nombreEmpleado}"?`)) return;

    this.loading = true;

    this.http.delete(`${this.base}/${row.idEmpleadoProyecto}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo desactivar';
      }
    });
  }

  toggleEstado(row: EmpleadoProyecto): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el cliente "${row.idEmpleadoProyecto}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.idEmpleadoProyecto}/estado`, {}).subscribe({
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
