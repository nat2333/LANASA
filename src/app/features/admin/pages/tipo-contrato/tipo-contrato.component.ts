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
  tipos: TipoContrato[] = [];
  loading = false;
  saving = false;
  deleting = false;
  editMode = false;
  error: string | null = null;

  private base = `${environment.apiUrl}/tipo-contrato`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      nombreTipocontrato: ['', [Validators.required, Validators.maxLength(100)]],
      estado: [true]
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.http.get<TipoContrato[]>(this.base).subscribe({
      next: data => {
        this.tipos = data || [];
        this.loading = false;
      },
      error: e => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudieron cargar los tipos de contrato';
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;
    this.saving = true;
    this.error = null;

    if (this.editMode && v.id) {
      const payload = { nombreTipocontrato: v.nombreTipocontrato };
      this.http.put(`${this.base}/${v.id}`, payload).subscribe({
        next: () => {
          this.saving = false;
          this.cancel();
          this.load();
        },
        error: e => {
          console.error(e);
          this.saving = false;
          this.error = 'No se pudo actualizar';
        }
      });
    } else {
      const payload = { nombreTipocontrato: v.nombreTipocontrato };
      this.http.post(this.base, payload).subscribe({
        next: () => {
          this.saving = false;
          this.cancel();
          this.load();
        },
        error: e => {
          console.error(e);
          this.saving = false;
          this.error = 'No se pudo crear';
        }
      });
    }
  }

  edit(row: TipoContrato): void {
    this.editMode = true;
    this.form.setValue({
      id: row.id,
      nombreTipocontrato: row.nombreTipocontrato,
      estado: row.estado
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      nombreTipocontrato: '',
      estado: true
    });
  }

  desactivar(row: TipoContrato): void {
    if (!confirm(`¿Desactivar tipo de contrato "${row.nombreTipocontrato}"?`)) return;

    this.deleting = true;

    this.http.delete(`${this.base}/${row.id}`).subscribe({
      next: () => {
        this.deleting = false;
        this.load();
      },
      error: e => {
        console.error(e);
        this.deleting = false;
        this.error = 'No se pudo desactivar';
      }
    });
  }
}
