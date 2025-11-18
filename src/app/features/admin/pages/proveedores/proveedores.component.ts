import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {
  ProveedorService,
  Proveedor,
} from '../../../../shared/services/proveedor.service';

@Component({
  selector: 'app-proveedores',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedores.component.html',
  styleUrls: ['./proveedores.component.scss'],
})
export class ProveedoresComponent implements OnInit {
  form!: FormGroup;
  proveedores: Proveedor[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private proveedorService: ProveedorService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadProveedores();
  }

private buildForm(): void {
  this.form = this.fb.group({
    rut: [
      '',
      [
        Validators.required,
        Validators.maxLength(40),
        Validators.pattern(/^[A-Za-z0-9.\-_/]+$/),
      ],
    ],
    nombreComercial: [
      '',
      [Validators.required, Validators.maxLength(150)],
    ],
    telefono: [
      '',
      [
        Validators.maxLength(40),
        Validators.pattern(/^[0-9+\-\s()]*$/),
      ],
    ],
    correo: ['', [Validators.email, Validators.maxLength(150)]],
    direccion: ['', [Validators.maxLength(150)]],
    ciudad: ['', [Validators.maxLength(80)]],
    pais: ['', [Validators.maxLength(80)]],
    categoria: ['', [Validators.maxLength(80)]],
    calificacion: [null, [Validators.min(0), Validators.max(5)]],
  });
}


  loadProveedores(): void {
    this.loading = true;
    this.error = null;

    this.proveedorService.getAll().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar proveedores';
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const body = this.form.value;

    if (this.editMode && this.editingId !== null) {
      this.updateProveedor(this.editingId, body);
    } else {
      this.createProveedor(body);
    }
  }

  private createProveedor(body: any): void {
    this.loading = true;
    this.error = null;

    this.proveedorService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadProveedores();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al crear proveedor';
        this.loading = false;
      },
    });
  }

  private updateProveedor(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.proveedorService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadProveedores();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al actualizar proveedor';
        this.loading = false;
      },
    });
  }

  editProveedor(p: Proveedor): void {
    this.editMode = true;
    this.editingId = p.idProveedor;

    this.form.patchValue({
      rut: p.rut,
      nombreComercial: p.nombreComercial,
      telefono: p.telefono,
      correo: p.correo,
      direccion: p.direccion,
      ciudad: p.ciudad,
      pais: p.pais,
      categoria: p.categoria,
      calificacion: p.calificacion,
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.resetForm();
  }

  private resetForm(): void {
    this.form.reset({
      rut: '',
      nombreComercial: '',
      telefono: '',
      correo: '',
      direccion: '',
      ciudad: '',
      pais: '',
      categoria: '',
      calificacion: null,
    });
  }

  deleteProveedor(p: Proveedor): void {
    if (!confirm(`¿Eliminar el proveedor "${p.nombreComercial}"?`)) return;

    this.loading = true;
    this.error = null;

    this.proveedorService.delete(p.idProveedor).subscribe({
      next: () => {
        this.loading = false;
        this.loadProveedores();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al eliminar proveedor';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: Proveedor): void {
    const accion = row.estado ? 'desactivar' : 'activar';
  
    if (!confirm(`¿Está seguro de ${accion} el estado "${row.idProveedor}"?`)) {
      return;
    }
  
    this.loading = true;
    this.error = null;
  
    this.proveedorService.toggleEstado(row.idProveedor).subscribe({
      next: () => {
        this.loading = false;
        this.loadProveedores();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el producto`;
      }
    });
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
