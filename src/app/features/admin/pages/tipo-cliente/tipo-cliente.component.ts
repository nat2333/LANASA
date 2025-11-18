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

interface TipoCliente {
  id: number;
  tipo: string;
  estado: boolean;
}

@Component({
  selector: 'app-tipo-cliente',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './tipo-cliente.component.html',
  styleUrls: ['./tipo-cliente.component.scss']
})
export class TipoClienteComponent implements OnInit {

  form!: FormGroup;

  tipos: TipoCliente[] = [];
  loading = false;
  saving = false;
  deleting = false;
  editMode = false;
  error: string | null = null;

  private base = `${environment.apiUrl}/tipo-cliente`;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      id: [null],
      tipo: ['', [Validators.required, Validators.maxLength(40)]],
      estado: [true]
    });

    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;

    this.http.get<TipoCliente[]>(this.base).subscribe({
      next: data => {
        this.tipos = data || [];
        this.loading = false;
      },
      error: e => {
        console.error(e);
        this.loading = false;
        this.error = 'No se pudieron cargar los tipos de cliente';
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;

    const v = this.form.value;
    this.saving = true;
    this.error = null;

    if (this.editMode && v.id) {
      const payload = { tipo: v.tipo };
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
      const payload = { tipo: v.tipo };
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

  edit(row: TipoCliente): void {
    this.editMode = true;
    this.form.setValue({
      id: row.id,
      tipo: row.tipo,
      estado: row.estado
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.form.reset({
      id: null,
      tipo: '',
      estado: true
    });
  }

  desactivar(row: TipoCliente): void {
    if (!confirm(`¿Desactivar tipo "${row.tipo}"?`)) return;

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

  toggleEstado(row: TipoCliente): void {
    const accion = row.estado ? 'desactivar' : 'activar';
    if (!confirm(`¿Está seguro de ${accion} el tipo de contrato "${row.tipo}"?`)) return;

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
