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

interface TipoProyecto {
  idTipoProyecto: number;
  tipoProyecto: string;
  estado: boolean;
}

@Component({
  selector: 'app-proyecto-tipo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proyecto-tipo.component.html',
  styleUrls: ['./proyecto-tipo.component.scss'],
})
export class ProyectoTipoComponent implements OnInit {
  form!: FormGroup;
  tipos: TipoProyecto[] = [];

  loading = false;
  saving = false;
  error: string | null = null;
  editMode = false;

  private base = `${environment.apiUrl}/tipo-proyecto`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      idTipoProyecto: [null],
      tipoProyecto: ['', [Validators.required, Validators.maxLength(80)]],
      estado: [true, Validators.required], // solo para mostrar
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.http.get<TipoProyecto[]>(this.base).subscribe({
      next: (data) => {
        this.tipos = data || [];
        this.loading = false;
      },
      error: (e) => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudo cargar tipos de proyecto';
      },
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;
    const payload = { tipoProyecto: v.tipoProyecto };

    this.saving = true;
    this.error = null;

    let req;
    if (this.editMode && v.idTipoProyecto) {
      req = this.http.put(`${this.base}/${v.idTipoProyecto}`, payload);
    } else {
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
        this.error = 'No se pudo guardar el tipo de proyecto';
      },
    });
  }

  edit(row: TipoProyecto): void {
    this.editMode = true;
    this.form.setValue({
      idTipoProyecto: row.idTipoProyecto,
      tipoProyecto: row.tipoProyecto,
      estado: row.estado ?? true,
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    if (this.form) {
      this.form.reset({
        idTipoProyecto: null,
        tipoProyecto: '',
        estado: true,
      });
    }
  }

  toggleEstado(row: TipoProyecto): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el cliente "${row.idTipoProyecto}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.idTipoProyecto}/estado`, {}).subscribe({
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
