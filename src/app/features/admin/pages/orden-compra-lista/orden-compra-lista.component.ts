import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import {
  OrdenCompraService,
  OrdenCompra,
} from '../../../../shared/services/orden-compra.service';
import {
  CompraEstadoService,
  EstadoCompra,
} from '../../../../shared/services/compra-estado.service';

@Component({
  selector: 'app-orden-compra-lista',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './orden-compra-lista.component.html',
  styleUrls: ['./orden-compra-lista.component.scss'],
})
export class OrdenCompraListaComponent implements OnInit {
  form!: FormGroup;
  ordenes: OrdenCompra[] = [];
  estadosCompra: EstadoCompra[] = [];   // <- aquí guardamos los estados

  loading = false;
  error: string | null = null;
  editMode = false;
  editingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private ordenCompraService: OrdenCompraService,
    private compraEstadoService: CompraEstadoService,   // <- nuevo
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.loadEstados();
    this.loadOrdenes();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      idProveedor: [null, [Validators.required, Validators.min(1)]],
      idProyecto: [null, [Validators.min(1)]],
      fechaOrden: ['', [Validators.required]],
      fechaEntregaEsperada: [''],
      fechaEntregaReal: [''],
      idEstadoCompra: [null, [Validators.required]],
    });
  }

  private loadEstados(): void {
    this.compraEstadoService.getAll().subscribe({
      next: (data) => {
        // si quieres solo activos, podrías filtrar aquí
        this.estadosCompra = data;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al cargar estados de compra';
      },
    });
  }

  loadOrdenes(): void {
    this.loading = true;
    this.error = null;

    this.ordenCompraService.getAll().subscribe({
      next: (data) => {
        this.ordenes = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Error al cargar órdenes de compra';
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
      this.updateOrden(this.editingId, body);
    } else {
      this.createOrden(body);
    }
  }

  private createOrden(body: any): void {
    this.loading = true;
    this.error = null;

    this.ordenCompraService.create(body).subscribe({
      next: () => {
        this.loading = false;
        this.resetForm();
        this.loadOrdenes();
      },
      error: () => {
        this.error = 'Error al crear orden de compra';
        this.loading = false;
      },
    });
  }

  private updateOrden(id: number, body: any): void {
    this.loading = true;
    this.error = null;

    this.ordenCompraService.update(id, body).subscribe({
      next: () => {
        this.loading = false;
        this.cancelEdit();
        this.loadOrdenes();
      },
      error: () => {
        this.error = 'Error al actualizar orden de compra';
        this.loading = false;
      },
    });
  }

  editOrden(o: OrdenCompra): void {
    this.editMode = true;
    this.editingId = o.idOrdenCompra;

    // intentamos mapear el nombre de estado al id usando la lista
    const estado = this.estadosCompra.find(
      e => e.estadoCompra === o.nombreEstadoCompra
    );

    this.form.patchValue({
      idProveedor: null,           // si tu DTO no trae el id, lo dejamos solo para creación
      idProyecto: o.idProyecto,
      fechaOrden: o.fechaOrden,
      fechaEntregaEsperada: o.fechaEntregaEsperada,
      fechaEntregaReal: o.fechaEntregaReal,
      idEstadoCompra: estado ? estado.id : null,
    });

    // si no quieres que cambien proveedor ni fecha de orden en edición:
    this.form.get('idProveedor')?.disable({ emitEvent: false });
    this.form.get('fechaOrden')?.disable({ emitEvent: false });

    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.editingId = null;
    this.form.enable({ emitEvent: false });  // re-habilitar todo
    this.resetForm();
  }

  private resetForm(): void {
    this.form.reset({
      idProveedor: null,
      idProyecto: null,
      fechaOrden: '',
      fechaEntregaEsperada: '',
      fechaEntregaReal: '',
      idEstadoCompra: null,
    });
  }

  deleteOrden(o: OrdenCompra): void {
    if (!confirm(`¿Eliminar la orden ${o.numero}?`)) return;

    this.loading = true;
    this.error = null;

    this.ordenCompraService.delete(o.idOrdenCompra).subscribe({
      next: () => {
        this.loading = false;
        this.loadOrdenes();
      },
      error: () => {
        this.error = 'Error al eliminar orden de compra';
        this.loading = false;
      },
    });
  }

  toggleEstado(row: OrdenCompra): void {
    const accion = row.estado ? 'desactivar' : 'activar';

    if (!confirm(`¿Está seguro de ${accion} el estado de la orden "${row.numero}"?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.ordenCompraService.toggleEstado(row.idOrdenCompra).subscribe({
      next: () => {
        this.loading = false;
        this.loadOrdenes();
      },
      error: err => {
        console.error(err);
        this.loading = false;
        this.error = `No se pudo ${accion} la orden de compra`;
      }
    });
  }

  hasError(control: string, error: string): boolean {
    const c = this.form.get(control);
    return !!c && c.touched && c.hasError(error);
  }
}
