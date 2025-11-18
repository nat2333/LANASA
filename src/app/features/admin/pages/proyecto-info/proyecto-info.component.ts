import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';

interface ProyectoDTO {
  idProyecto: number;
  codigo: string;
  nombre: string;
  descripcion: string | null;
  fechaInicio: string;           // ISO (LocalDate)
  fechaFinEstimada: string | null;
  fechaFinReal: string | null;
  presupuestoAprobado: number | null;
  presupuestoUtilizado: number | null;
  estado: boolean;
  idCliente: number;
  correoCliente: string;
  idDepartamento: number;
  nombreDepartamento: string;
  idTipoProyecto: number;
  nombreTipoProyecto: string;
}

@Component({
  selector: 'app-proyecto-info',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proyecto-info.component.html',
  styleUrls: ['./proyecto-info.component.scss'],
})
export class ProyectoInfoComponent implements OnInit {

  proyecto: ProyectoDTO | null = null;
  form!: FormGroup;

  loading = false;
  saving = false;
  deleting = false;
  error: string | null = null;

  private id!: number;
  private base = `${environment.apiUrl}/proyectos`;   // backend: /proyectos/{id}

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    this.form = this.fb.group({
      fechaFinReal: [''],
      presupuestoAprobado: [''],
      presupuestoUtilizado: [''],
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.http.get<ProyectoDTO>(`${this.base}/${this.id}`).subscribe({
      next: (p) => {
        this.proyecto = p;
        this.loading = false;

        this.form.patchValue({
          fechaFinReal: p.fechaFinReal ? p.fechaFinReal.substring(0, 10) : '',
          presupuestoAprobado: p.presupuestoAprobado ?? '',
          presupuestoUtilizado: p.presupuestoUtilizado ?? '',
        });
      },
      error: (e) => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudo cargar la información del proyecto';
      },
    });
  }

  submit(): void {
    if (!this.proyecto) return;

    this.saving = true;
    this.error = null;

    const v = this.form.value;

    const payload = {
      fechaFinReal: v.fechaFinReal || null,
      presupuestoAprobado:
        v.presupuestoAprobado !== '' && v.presupuestoAprobado != null
          ? Number(v.presupuestoAprobado)
          : null,
      presupuestoUtilizado:
        v.presupuestoUtilizado !== '' && v.presupuestoUtilizado != null
          ? Number(v.presupuestoUtilizado)
          : null,
    };

    this.http.put(`${this.base}/${this.proyecto.idProyecto}`, payload).subscribe({
      next: () => {
        this.saving = false;
        this.load();   // recargar datos actualizados
      },
      error: (e) => {
        console.error(e);
        this.saving = false;
        this.error = 'No se pudo actualizar el proyecto';
      },
    });
  }

  toggleEstado(row: ProyectoDTO): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el cliente "${row.idProyecto}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.idProyecto}/estado`, {}).subscribe({
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
