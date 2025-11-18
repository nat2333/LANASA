import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

import {
  ProveedorProductoService,
  ProveedorProducto,
} from '../../../../shared/services/proveedor-producto.service';

@Component({
  selector: 'app-proveedores-productos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedores-productos.component.html',
  styleUrls: ['./proveedores-productos.component.scss'],
})
export class ProveedoresProductosComponent implements OnInit {
  form!: FormGroup;
  relaciones: ProveedorProducto[] = [];

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private proveedorProductoService: ProveedorProductoService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadRelaciones();
    this.setCreateMode();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      idProducto: [
        null,
        [Validators.required, Validators.min(1)],
      ],
      idProveedor: [
        null,
        [Validators.required, Validators.min(1)],
      ],
      // asumimos misma escala que proveedor: 0 a 5
      calificacion: [null, [Validators.min(0), Validators.max(5)]],
    });
  }

  private setCreateMode(): void {
    this.editMode = false;
    this.editingId = null;

    this.form.enable({ emitEvent: false });

    this.form.reset({
      idProducto: null,
      idProveedor: null,
      calificacion: null,
    });
  }

  private setEditMode(r: ProveedorProducto): void {
    this.editMode = true;
    this.editingId = r.idProveedorProducto;

    this.form.patchValue({
      idProducto: r.idProducto,
      idProveedor: r.idProveedor,
      calificacion: r.calificacion,
    });

    // que no puedan cambiar los IDs desde el front
    this.form.get('idProducto')?.disable({ emitEvent: false });
    this.form.get('idProveedor')?.disable({ emitEvent: false });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  loadRelaciones(): void {
    this.loading = true;
    this.error = null;

    this.proveedorProductoService.getAll().subscribe({
      next: (data) => {
        this.relaciones = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar proveedores de productos';
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // getRawValue incluye los campos deshabilitados (IDs en modo edición)
    const body = this.form.getRawValue();

    if (this.editMode && this.editingId !== null) {
      this.updateRelacion(this.editingId, body);
    } else {
      this.createRelacion(body);
    }
  }

  private createRelacion(body: any): void {
    this.loading = true;
    this.error = null;

    this.proveedorProductoService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.setCreateMode();
        this.loadRelaciones();
      },
      error: () => {
        this.error = 'Error al crear relación proveedor-producto';
        this.loading = false;
      },
    });
  }

  private updateRelacion(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.proveedorProductoService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.setCreateMode();
        this.loadRelaciones();
      },
      error: () => {
        this.error = 'Error al actualizar relación proveedor-producto';
        this.loading = false;
      },
    });
  }

  editRelacion(r: ProveedorProducto): void {
    this.setEditMode(r);
  }

  deleteRelacion(r: ProveedorProducto): void {
    if (
      !confirm(
        `¿Eliminar relación entre proveedor "${r.nombreComercialProveedor}" y producto "${r.nombreProducto}"?`
      )
    ) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.proveedorProductoService.delete(r.idProveedorProducto).subscribe({
      next: () => {
        this.loading = false;
        this.loadRelaciones();
      },
      error: () => {
        this.error = 'Error al eliminar la relación';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: ProveedorProducto): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (
      !confirm(
        `¿Está seguro de ${accion} la relación "${row.idProveedorProducto}"?`
      )
    ) {
      return;
    }

    this.loading = true;
    this.error = null;

    // ⚠️ aquí había un bug: se enviaba idProveedor
    this.proveedorProductoService.toggleEstado(row.idProveedorProducto).subscribe({
      next: () => {
        this.loading = false;
        this.loadRelaciones();
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} el proveedor-producto`;
      },
    });
  }

  cancelEdit(): void {
    this.setCreateMode();
  }

  resetForm(): void {
    this.setCreateMode();
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
