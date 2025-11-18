import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ProyectoService } from '../../../../shared/services/proyecto.service';

@Component({
  selector: 'app-proyectos-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './proyectos-list.component.html',
  styleUrls: ['./proyectos-list.component.scss'],
})
export class ProyectosListComponent implements OnInit {
  form!: FormGroup;

  proyectos: any[] = [];
  loading = false;
  saving = false;
  deleting = false;
  error: string | null = null;
  editMode = false;

  constructor(
    private fb: FormBuilder,
    private api: ProyectoService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      idProyecto: [null],              // sólo se usa en edición
      idTipoProyecto: [null, Validators.required],
      idCliente: [null, Validators.required],
      idDepartamento: [null, Validators.required],
      nombre: ['', [Validators.required, Validators.maxLength(150)]],
      descripcion: [''],
      fechaInicio: ['', Validators.required],
      fechaFinEstimada: [''],
      fechaFinReal: [''],
      presupuestoAprobado: [null],
      presupuestoUtilizado: [null],
      estado: [true],
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.api.getAll().subscribe({
      next: (data) => {
        this.proyectos = data || [];
        this.loading = false;
      },
      error: (e) => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudieron cargar los proyectos';
      },
    });
  }

  /** Crear o actualizar */
  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;
    this.saving = true;
    this.error = null;

    if (this.editMode && v.idProyecto) {
      // Actualizar => usar ActualizarProyectoRequest
      const payload = {
        idTipoProyecto: v.idTipoProyecto,
        idCliente: v.idCliente,
        idDepartamento: v.idDepartamento,
        nombre: v.nombre,
        descripcion: v.descripcion || null,
        fechaInicio: v.fechaInicio,
        fechaFinEstimada: v.fechaFinEstimada || null,
        fechaFinReal: v.fechaFinReal || null,
        presupuestoAprobado: v.presupuestoAprobado ?? null,
        presupuestoUtilizado: v.presupuestoUtilizado ?? null,
      };

      this.api.update(v.idProyecto, payload).subscribe({
        next: () => {
          this.saving = false;
          this.cancel();
          this.load();
        },
        error: (e) => {
          console.error(e);
          this.saving = false;
          this.error = 'No se pudo actualizar el proyecto';
        },
      });
    } else {
      // Crear => CrearProyectoRequest
      const payload = {
        idTipoProyecto: v.idTipoProyecto,
        idCliente: v.idCliente,
        idDepartamento: v.idDepartamento,
        nombre: v.nombre,
        descripcion: v.descripcion || null,
        fechaInicio: v.fechaInicio,
        fechaFinEstimada: v.fechaFinEstimada || null,
        presupuestoAprobado: v.presupuestoAprobado ?? null,
        presupuestoUtilizado: v.presupuestoUtilizado ?? null,
      };

      this.api.create(payload).subscribe({
        next: () => {
          this.saving = false;
          this.cancel();
          this.load();
        },
        error: (e) => {
          console.error(e);
          this.saving = false;
          this.error = 'No se pudo crear el proyecto';
        },
      });
    }
  }

  /** Rellenar formulario para edición */
  edit(p: any): void {
    this.editMode = true;

    this.form.setValue({
      idProyecto: p.idProyecto ?? null,
      idTipoProyecto: p.idTipoProyecto ?? null,
      idCliente: p.idCliente ?? null,
      idDepartamento: p.idDepartamento ?? null,
      nombre: p.nombre ?? '',
      descripcion: p.descripcion ?? '',
      fechaInicio: p.fechaInicio ?? '',
      fechaFinEstimada: p.fechaFinEstimada ?? '',
      fechaFinReal: p.fechaFinReal ?? '',
      presupuestoAprobado: p.presupuestoAprobado ?? null,
      presupuestoUtilizado: p.presupuestoUtilizado ?? null,
      estado: p.estado ?? true,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    if (this.form) {
      this.form.reset({
        idProyecto: null,
        idTipoProyecto: null,
        idCliente: null,
        idDepartamento: null,
        nombre: '',
        descripcion: '',
        fechaInicio: '',
        fechaFinEstimada: '',
        fechaFinReal: '',
        presupuestoAprobado: null,
        presupuestoUtilizado: null,
        estado: true,
      });
    }
  }

  desactivar(p: any): void {
    if (!p.idProyecto) return;
    if (!confirm(`¿Desactivar proyecto "${p.nombre}"?`)) return;

    this.deleting = true;
    this.error = null;

    this.api.delete(p.idProyecto).subscribe({
      next: () => {
        this.deleting = false;
        this.load();
      },
      error: (e) => {
        console.error(e);
        this.deleting = false;
        this.error = 'No se pudo desactivar el proyecto';
      },
    });
  }
}
