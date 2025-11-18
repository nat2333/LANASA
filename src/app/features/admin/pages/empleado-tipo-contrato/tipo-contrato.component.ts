import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../../environments/environment';

interface TipoContrato {
  id: number;
  nombreTipocontrato: string;
  estado: boolean;
}

@Component({
  selector: 'app-tipo-contrato',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './tipo-contrato.component.html',
  styleUrls: ['./tipo-contrato.component.scss']
})

export class TipoContratoComponent implements OnInit {
  form!: FormGroup;
  tiposContrato: TipoContrato[] = [];
  loading = false;
  error: string | null = null;
  editMode = false;

  private base = `${environment.apiUrl}/tipo-contrato`; 

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      nombreTipocontrato: ['', [Validators.required, Validators.maxLength(100)]]
    });
    this.load();
  }

  load(): void {
    this.loading = true;
    this.http.get<TipoContrato[]>(this.base).subscribe({
      next: data => {
        console.log('✅ Respuesta recibida:', data);
        this.tiposContrato = data || [];
        this.loading = false;
        this.error = null;
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo cargar los tipos de contrato';
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    const v = this.form.value as any;
    let req;

    if (this.editMode && v.id != null) {
      const payload = {
        nombreTipocontrato: v.nombreTipocontrato
      };
      req = this.http.put(`${this.base}/${v.id}`, payload);
    } else {
      const payload = {
        nombreTipocontrato: v.nombreTipocontrato
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
      }
    });
  }

   edit(row: TipoContrato): void {
    this.editMode = true;
    this.form.patchValue({
      id: row.id,
      nombreTipocontrato: row.nombreTipocontrato
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      nombreTipocontrato: ''
    });
  }

  remove(row: TipoContrato): void {
    if (!confirm(`¿Eliminar contrato del empleado ${row.nombreTipocontrato}?`)) return;

    this.loading = true;
    this.http.delete<void>(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = 'No se pudo eliminar';
      }
    });
  }

  toggleEstado(row: TipoContrato): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el tipo de contrato "${row.nombreTipocontrato}"?`)) return;

    this.loading = true;
    this.http.post(`${this.base}/${row.id}/estado`, {}).subscribe({
      next: () => {
        this.loading = false;
        this.load();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el tipo de contrato`;
      },
    });
  }
}
