import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

type Row = Record<string, any>;

@Component({
  selector: 'app-table-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './table-page.component.html',
  styleUrls: ['./table-page.component.scss'],
})
export class TablePageComponent implements OnInit {
  title = 'Tabla';
  endpoint = '';

  columns: string[] = [];
  rows: Row[] = [];

  form!: FormGroup;
  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: any = null;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const data = this.route.snapshot.data as {
      title?: string;
      endpoint?: string;
    };

    this.title = data.title ?? 'Tabla';
    this.endpoint = data.endpoint ?? '';

    this.load();
  }

  private buildFormFromColumns(): void {
    const group: Record<string, any> = {};
    this.columns.forEach(col => {
      group[col] = [''];
    });
    this.form = this.fb.group(group);
  }

  load(): void {
    if (!this.endpoint) {
      this.error = 'Endpoint no configurado';
      return;
    }

    this.loading = true;
    this.http
      .get<Row[]>(`${environment.apiUrl}/${this.endpoint}`)
      .subscribe({
        next: data => {
          this.rows = data || [];

          if (this.rows.length && this.columns.length === 0) {
            this.columns = Object.keys(this.rows[0]);
          }

          if (!this.form) {
            this.buildFormFromColumns();
          }

          this.loading = false;
          this.error = null;
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = 'No se pudo cargar';
        },
      });
  }

  submit(): void {
    if (!this.form || this.form.invalid || !this.endpoint) return;

    const value = this.form.value;
    let req;

    if (this.editMode && this.editingId != null) {
      req = this.http.put(
        `${environment.apiUrl}/${this.endpoint}/${this.editingId}`,
        value
      );
    } else {
      req = this.http.post(
        `${environment.apiUrl}/${this.endpoint}`,
        value
      );
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
      },
    });
  }

  edit(r: Row): void {
    this.editMode = true;

    const id =
      r['id'] ??
      r['id_registro'] ??
      r['id_empleado'] ??
      r['id_proyecto'] ??
      r['id_cliente'] ??
      null;

    this.editingId = id;

    if (!this.form) {
      this.buildFormFromColumns();
    }
    this.form.patchValue(r);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancel(): void {
    this.editMode = false;
    this.editingId = null;
    if (this.form) {
      this.form.reset();
    }
  }

  remove(r: Row): void {
    const id =
      r['id'] ??
      r['id_registro'] ??
      r['id_empleado'] ??
      r['id_proyecto'] ??
      r['id_cliente'] ??
      null;

    if (id == null || !this.endpoint) {
      alert('No se puede eliminar: fila sin id o sin endpoint');
      return;
    }

    if (!confirm('¿Eliminar registro?')) return;

    this.loading = true;
    this.http
      .delete<void>(`${environment.apiUrl}/${this.endpoint}/${id}`)
      .subscribe({
        next: () => {
          this.loading = false;
          this.load();
        },
        error: err => {
          console.error(err);
          this.loading = false;
          this.error = 'No se pudo eliminar';
        },
      });
  }
}
